package com.quantice.dataaggregator.schedule;

import com.quantice.dataaggregator.rss.RssChannelReader;
import com.quantice.dataaggregator.rss.RssUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RssSchedule {

    private final RssUtils rssUtils;
    private final RssChannelReader rssChannelReader;

    /**
     * Fetch all data from rss after every 60 minutes
     *
     */
    @Scheduled(fixedRate = 3600000)
    public void aggregateRss() {
        rssUtils.getChannels().stream().map(
            channel -> rssChannelReader.readChannel(channel).subscribe()
        ).toList();
    }

}