package com.quantice.dataaggregator.rss;

import com.quantice.dataaggregator.model.Entry;
import com.rometools.rome.feed.synd.SyndFeed;
import java.util.List;
import reactor.core.publisher.Flux;

public interface RssChannelReader {

    Flux<Entry> readChannel(String url);

    List<Entry> readEntries(SyndFeed syndFeed);

}
