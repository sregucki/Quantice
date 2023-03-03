package com.quantice.dataaggregator.rss;

import com.rometools.rome.feed.synd.SyndFeed;
import java.util.List;
import java.util.Optional;

public interface RssUtils {

    boolean isActive(String url);

    Optional<SyndFeed> getParsingResult(String url);

    List<String> getChannels();

}
