package com.quantice.dataaggregator.rss;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.quantice.dataaggregator.config.RssProperties;
import com.quantice.dataaggregator.model.Channel;
import com.quantice.dataaggregator.model.Entry;
import com.quantice.dataaggregator.model.Language;
import com.quantice.dataaggregator.model.Topic;
import com.quantice.dataaggregator.repository.ChannelRepository;
import com.quantice.dataaggregator.repository.EntryRepository;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndFeed;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Component
public class RssChannelReaderImpl implements RssChannelReader {

    private final RssUtils rssUtils;
    private final EntryRepository entryRepository;
    private final ChannelRepository channelRepository;
    private final RssProperties rssProperties;
    private static final Logger LOGGER = LoggerFactory.getLogger(RssChannelReaderImpl.class);

    @Override
    public Flux<Entry> readChannel(String url) {

        Optional<SyndFeed> syndFeed = rssUtils.getParsingResult(url);

        if (!rssUtils.isActive(url)) {
            LOGGER.warn(String.format("Rss channel of url: %s is inactive", url));
            return Flux.empty();
        }
        if (syndFeed.isEmpty()) {
            LOGGER.warn(String.format("Rss channel of url: %s is not parsable", url));
            return Flux.empty();
        }

        List<Entry> entries = readEntries(syndFeed.get()).stream().filter(entry -> !entryRepository.existsByUrl(entry.getUrl()).block()).toList();
        return entryRepository.saveAll(entries);
    }

    private List<Entry> readEntries(SyndFeed syndFeed) {

        return syndFeed.getEntries().stream().map(rssEntry -> {

            Optional<SyndContent> description = Optional.ofNullable(rssEntry.getDescription());
            Instant publishedAt = null;
            if (rssEntry.getPublishedDate() != null) {
                publishedAt = rssEntry.getPublishedDate().toInstant();
            }
            String descriptionValue = description.map(syndContent -> Jsoup.parse(syndContent.getValue()).text()).orElse("");

            return Entry.builder()
                .url(rssEntry.getLink())
                .title(rssEntry.getTitle())
                .description(descriptionValue)
                .publishedAt(publishedAt)
                .build();

        }).toList();
    }

    @Bean
    @Override
    public void saveChannels() {

        if (channelRepository.count().block() > 0) {
            LOGGER.info("Skipping channel initialization - channel collection already populated");
            return;
        }
        List<List<String>> records = getCsvRecords(rssProperties.getChannelsFileName());

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
                return Channel.builder()
                    .url(url)
                    .language(language)
                    .topic(topic)
                    .build();
            }
            return null;
        }).filter(Objects::nonNull).toList();

        LOGGER.info("Saving channels from .csv file");
        channelRepository.saveAll(channels).subscribe();
    }

    private List<List<String>> getCsvRecords(String fileName) {

        List<List<String>> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(new ClassPathResource(fileName + ".csv").getInputStream()))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return records;
    }

}
