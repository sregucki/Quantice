package com.quantice.dataaggregator.rss;

import com.quantice.dataaggregator.model.Language;
import com.quantice.dataaggregator.repository.ChannelRepository;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class RssUtilsImpl implements RssUtils {

    private final SyndFeedInput syndFeedInput;
    private final ChannelRepository channelRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(RssUtilsImpl.class);

    @Override
    public Optional<SyndFeed> getParsingResult(String url) {

        try {

            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(false);

            SyndFeed syndFeed = syndFeedInput.build(new XmlReader(new URL(url).openStream()));

            return Optional.ofNullable(syndFeed);

        } catch (IOException | FeedException | IllegalArgumentException e) {

            LOGGER.warn(String.format("Error while processing channel of url: %s\nError message: %s", url, e.getMessage()));
            return Optional.empty();
        }

    }

    @Override
    public boolean isActive(String url) {

        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod("HEAD");

            if (httpURLConnection.getResponseCode() < 200 && httpURLConnection.getResponseCode() > 399) {
                return false;
            }

        } catch (IOException e) {

            LOGGER.warn(String.format("Error while processing channel of url: %s\nError message: %s", url, e.getMessage()));
            return false;
        }
        return true;
    }

    @Override
    public Flux<String> getChannelsUrls(Language language) {

        List<String> channels = new ArrayList<>(
            channelRepository.findAll().mapNotNull(channel -> {
                if (channel.getLanguage() == language) {
                    return channel.getUrl();
                }
                return null;
            }).filter(Objects::nonNull).toStream().toList());

        Collections.shuffle(channels);
        return Flux.fromIterable(channels);
    }

}
