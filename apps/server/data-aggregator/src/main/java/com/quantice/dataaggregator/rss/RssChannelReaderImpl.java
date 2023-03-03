package com.quantice.dataaggregator.rss;

import com.quantice.dataaggregator.model.Entry;
import com.quantice.dataaggregator.repository.EntryRepository;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndFeed;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Component
public class RssChannelReaderImpl implements RssChannelReader {

    private final RssUtils rssUtils;
    private final EntryRepository entryRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(RssChannelReaderImpl.class);

    @Override
    public Flux<Entry> readChannel(String url) {

        if (!rssUtils.isActive(url)) {
            LOGGER.error(String.format("Rss channel of url: %s is inactive", url));
            return Flux.empty();
        }
        if (rssUtils.getParsingResult(url).isEmpty()) {
            LOGGER.error(String.format("Rss channel of url: %s is not parsable", url));
            return Flux.empty();
        }

        SyndFeed syndFeed = rssUtils.getParsingResult(url).get();
        List<Entry> entries = readEntries(syndFeed).stream().filter(entry -> !entryRepository.existsByUrl(entry.getUrl()).block()).toList();

        return entryRepository.saveAll(entries);
    }

    private List<Entry> readEntries(SyndFeed syndFeed) {

        return syndFeed.getEntries().stream().map(rssEntry -> {

            Optional<SyndContent> description = Optional.ofNullable(rssEntry.getDescription());
            String descriptionValue = description.map(syndContent -> Jsoup.parse(syndContent.getValue()).text()).orElse("");

            return Entry.builder()
                .url(rssEntry.getLink())
                .title(rssEntry.getTitle())
                .description(descriptionValue)
                .author(rssEntry.getAuthor())
                .publishedAt(rssEntry.getPublishedDate().toInstant())
                .build();

        }).toList();
    }

}
