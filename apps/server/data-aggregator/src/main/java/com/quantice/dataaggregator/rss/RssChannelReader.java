package com.quantice.dataaggregator.rss;

import com.quantice.dataaggregator.model.Entry;
import reactor.core.publisher.Flux;

public interface RssChannelReader {

    Flux<Entry> readChannel(String url);

    void saveChannels();

}
