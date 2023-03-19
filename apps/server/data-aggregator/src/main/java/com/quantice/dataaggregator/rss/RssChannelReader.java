package com.quantice.dataaggregator.rss;

import com.quantice.dataaggregator.model.Channel;
import com.quantice.dataaggregator.model.Entry;
import java.util.List;

public interface RssChannelReader {

    List<Entry> readChannel(String url);

    List<Channel> saveChannels();

}
