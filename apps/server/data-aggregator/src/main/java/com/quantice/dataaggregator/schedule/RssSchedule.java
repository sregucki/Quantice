package com.quantice.dataaggregator.schedule;

import com.quantice.dataaggregator.rss.RssChannelReader;
import com.quantice.dataaggregator.rss.RssUtils;
import java.io.IOException;
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
     * @throws IOException
     */
    @Scheduled(fixedRate = 3600000)
    public void aggregateRss() throws IOException {
        rssUtils.getChannels().forEach(rssChannelReader::readChannel);
    }


}
