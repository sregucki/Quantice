package com.quantice.dataaggregator.rss;

import com.quantice.dataaggregator.model.Entry;
import com.quantice.dataaggregator.model.Language;
import com.rometools.rome.feed.synd.SyndFeed;
import java.util.List;
import java.util.Optional;

public interface RssUtils {

    boolean isActive(String url);

    Optional<SyndFeed> getParsingResult(String url);

    List<String> getChannelsUrls(Language language);

    List<List<String>> getCsvRecords(String fileName);

    List<Entry> readEntries(SyndFeed syndFeed);

    long getNumberOfChannels(Language language);

}
