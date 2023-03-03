package com.quantice.dataaggregator.rss;

import com.google.gson.Gson;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RssUtilsImpl implements RssUtils {

    private final SyndFeedInput syndFeedInput;
    private final Gson gson;
    public static final String channelsFile = "channels.json";
    private static final Logger LOGGER = LoggerFactory.getLogger(RssUtilsImpl.class);

    @Override
    public Optional<SyndFeed> getParsingResult(String url) {

        try {

            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(false);

            SyndFeed syndFeed = syndFeedInput.build(new XmlReader(new URL(url).openStream()));

            return Optional.ofNullable(syndFeed);

        } catch (IOException | FeedException e) {

            LOGGER.error(String.format("Error while processing channel of url: %s\nError message: %s", url, e.getMessage()));
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

            LOGGER.error(String.format("Error while processing channel of url: %s\nError message: %s", url, e.getMessage()));
            return false;
        }

        return true;
    }

    @Override
    public List<String> getChannels() throws IOException {

        ClassPathResource resource = new ClassPathResource(channelsFile);
        Reader reader = new InputStreamReader(resource.getInputStream());

        Map<String, List<String>> json = gson.fromJson(reader, Map.class);

        reader.close();
        return json.get("channels");
    }

}
