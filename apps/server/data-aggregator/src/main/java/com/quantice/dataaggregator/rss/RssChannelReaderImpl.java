package com.quantice.dataaggregator.rss;

import com.quantice.dataaggregator.config.RssProperties;
import com.quantice.dataaggregator.model.Channel;
import com.quantice.dataaggregator.model.Entry;
import com.quantice.dataaggregator.model.Language;
import com.quantice.dataaggregator.model.Topic;
import com.quantice.dataaggregator.repository.ChannelRepository;
import com.quantice.dataaggregator.repository.EntryRepository;
import com.rometools.rome.feed.synd.SyndFeed;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RssChannelReaderImpl implements RssChannelReader {

    private final RssUtils rssUtils;
    private final EntryRepository entryRepository;
    private final ChannelRepository channelRepository;
    private final RssProperties rssProperties;
    private static final Logger LOGGER = LoggerFactory.getLogger(RssChannelReaderImpl.class);

    @Override
    public List<Entry> readChannel(String url) {

        Optional<SyndFeed> syndFeed = rssUtils.getParsingResult(url);

        if (!rssUtils.isActive(url)) {
            LOGGER.warn(String.format("Rss channel of url: %s is inactive", url));
            return Collections.emptyList();
        }
        if (syndFeed.isEmpty()) {
            LOGGER.warn(String.format("Rss channel of url: %s is not parsable", url));
            return Collections.emptyList();
        }

        List<Entry> entries = rssUtils.readEntries(syndFeed.get())
            .stream()
            .filter(entry -> !entryRepository.existsByUrl(entry.getUrl()))
            .toList();

        return entryRepository.saveAll(entries);
    }

    @Bean
    @Override
    public List<Channel> saveChannels() {

        if (channelRepository.count() > 0) {
            LOGGER.info("Skipping channel initialization - channel collection already populated");
            return List.of();
        }

        List<List<String>> records = rssUtils.getCsvRecords(rssProperties.getChannelsFileName());

        List<Channel> channels = records.stream().map(record -> {
            Language language = null;
            Topic topic = null;
            String url = record.get(5);

            try {
                language = Language.valueOf(record.get(1).toUpperCase());
                topic = Topic.valueOf(record.get(2).toUpperCase());
            }
            catch (IllegalArgumentException ex) {
                // SKIPPING NON STOCK RELATED CHANNELS
            }

            if (language != null && topic != null && !url.isEmpty()) {
                Channel channel = new Channel();
                channel.setUrl(url);
                channel.setLanguage(language);
                channel.setTopic(topic);
                return channel;
            }
            return null;
        }).filter(Objects::nonNull).toList();

        LOGGER.info("Saving channels from .csv file");
        return channelRepository.saveAll(channels);
    }

}
