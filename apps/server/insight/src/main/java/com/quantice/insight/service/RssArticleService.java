package com.quantice.insight.service;

import com.quantice.insight.config.ArticleApiConstants;
import com.quantice.insight.model.Article;
import com.quantice.insight.model.Headline;
import com.quantice.insight.repository.ArticleRepository;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RssArticleService {

    private final ArticleRepository articleRepository;
    private final MongoTemplate mongoTemplate;
    private final SyndFeedInput syndFeedInput;
    private static final Logger LOGGER = LoggerFactory.getLogger(RssArticleService.class);

    public List<Article> findArticles(final List<String> keywords, final Optional<String> from, final Optional<String> to, final Optional<Integer> limit) {

        Instant fromInstant;
        Instant toInstant;

        if (from.isPresent()) {
            fromInstant = Instant.parse(from.get());
        }
        else {
            fromInstant = Instant.now().minus(60, ChronoUnit.DAYS);
        }

        if (to.isPresent()) {
            toInstant = Instant.parse(to.get());
        }
        else {
            toInstant = Instant.now();
        }

        Query query = TextQuery.queryText(getTextCriteriaMultipleMatch(keywords)).sortByScore();
        final List<Article> articles = new ArrayList<>(
            mongoTemplate.find(query, Article.class)
        );

        return articles
            .stream()
            .filter(article -> article.getPublishedAt() != null)
            .filter(article ->
                article.getPublishedAt().isAfter(fromInstant) && article.getPublishedAt().isBefore(toInstant) // Pick articles only within specified time range
            )
            .limit(limit.orElse(ArticleApiConstants.DEFAULT_LIMIT_ARTICLES.getArticlesLimit())) // Pick n articles with the best score
            .sorted(Comparator.comparing(Article::getPublishedAt).reversed()) // Sort articles by publish date
            .toList();
    }

    public long countArticles() {
        return articleRepository.count();
    }

    private TextCriteria getTextCriteriaMultipleMatch(List<String> keywords) {

        TextCriteria textCriteria = TextCriteria.forDefaultLanguage();

        for (String keyword: keywords) {
            try {
                Method method = textCriteria.getClass().getMethod("matchingPhrase", String.class);
                textCriteria = (TextCriteria) method.invoke(textCriteria, keyword);
            }
            catch (Exception e) {
                LOGGER.error("Error while chaining matchingPrase for keywords");
                e.printStackTrace();
            }
        }
        return textCriteria;
    }

    public List<Headline> getHeadlines() throws IOException, FeedException {
        String headlineChannelUrl = "https://finance.yahoo.com/rss/"; // TODO move it to properties

        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(headlineChannelUrl).openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setDoOutput(false);

        SyndFeed syndFeed = syndFeedInput.build(new XmlReader(new URL(headlineChannelUrl)));

        return syndFeed.getEntries().stream().map(rssEntry -> Headline.builder()
             .url(rssEntry.getLink())
             .title(rssEntry.getTitle())
             .imageUrl(rssEntry.getForeignMarkup().get(0).getAttributeValue("url"))
             .build()).toList();

    }

}
