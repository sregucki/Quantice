package com.quantice.dataaggregator.rss;

import com.quantice.dataaggregator.model.Language;
import com.rometools.rome.feed.synd.SyndFeed;
import java.util.Optional;
import reactor.core.publisher.Flux;

public interface RssUtils {

    boolean isActive(String url);

    Optional<SyndFeed> getParsingResult(String url);

    Flux<String> getChannelsUrls(Language language);

}
