package com.quantice.dataaggregator.rss;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.quantice.dataaggregator.model.Entry;
import com.quantice.dataaggregator.model.Language;
import com.quantice.dataaggregator.repository.ChannelRepository;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

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

            SyndFeed syndFeed = syndFeedInput.build(new XmlReader(new URL(url)));

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
    public List<Entry> readEntries(SyndFeed syndFeed) {

        return syndFeed.getEntries().stream().map(rssEntry -> {

            Optional<SyndContent> description = Optional.ofNullable(rssEntry.getDescription());
            Instant publishedAt = null;
            if (rssEntry.getPublishedDate() != null) {
                publishedAt = rssEntry.getPublishedDate().toInstant();
            }
            String descriptionValue = description.map(syndContent -> Jsoup.parse(syndContent.getValue()).text()).orElse("");

            return Entry.builder()
                .url(rssEntry.getLink())
                .title(rssEntry.getTitle())
                .description(descriptionValue)
                .publishedAt(publishedAt)
                .build();

        }).toList();
    }

    @Override
    public List<String> getChannelsUrls(Language language) {

        return new ArrayList<>(
            channelRepository.findAll().stream().map(channel -> {
                if (channel.getLanguage() == language) {
                    return channel.getUrl();
                }
                return null;
            }).filter(Objects::nonNull).toList());
    }

    @Override
    public List<List<String>> getCsvRecords(String fileName) {

        List<List<String>> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(new ClassPathResource(fileName + ".csv").getInputStream()))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return records;
    }

    @Override
    public long getNumberOfChannels(Language language) {

        return channelRepository.findAllByLanguage(language).size();
    }

}
