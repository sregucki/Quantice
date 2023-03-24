package com.quantice.dataaggregator.schedule;

import com.quantice.dataaggregator.config.RssProperties;
import com.quantice.dataaggregator.model.Channel;
import com.quantice.dataaggregator.repository.ChannelRepository;
import com.quantice.dataaggregator.rss.RssChannelReader;
import com.quantice.dataaggregator.rss.RssUtils;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RssSchedule {

    private final RssUtils rssUtils;
    private final RssProperties rssProperties;
    private final RssChannelReader rssChannelReader;
    private final ChannelRepository channelRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(RssSchedule.class);

    /**
     * Fetch all data from rss after every 60 minutes
     */
    @Scheduled(fixedRate = 360_000_0)
    public void fetchArticles() {

        long numberOfChannels = rssUtils.getNumberOfChannels(rssProperties.getLanguage());
        AtomicInteger parsedChannelCount = new AtomicInteger(0);

        rssUtils.getChannelsUrls(rssProperties.getLanguage())
            .forEach(url -> {
                LOGGER.info(String.format("Channels parsed: %d/%d", parsedChannelCount.incrementAndGet(), numberOfChannels));

                Channel currentChannel = channelRepository.findByUrl(url);
                currentChannel.setLastParsingAttempt(Instant.now());
                channelRepository.save(currentChannel);

                rssChannelReader.readChannel(url);
            });
    }

}
