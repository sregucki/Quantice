package com.quantice.insight.service;

import com.quantice.insight.config.ArticleApiConstants;
import com.quantice.insight.model.Article;
import com.quantice.insight.repository.ArticleRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(RssArticleService.class);

    public List<Article> findArticles(final String keyword, final Optional<String> from, final Optional<String> to, final Optional<Integer> limit) {

        Instant fromInstant;
        Instant toInstant;

        if (from.isPresent()) {
            fromInstant = Instant.parse(from.get());
        }
        else {
            fromInstant = Instant.now().minus(10, ChronoUnit.DAYS);
        }

        if (to.isPresent()) {
            toInstant = Instant.parse(to.get());
        }
        else {
            toInstant = Instant.now();
        }

        // Full text search for other languages needs to be set up here
        TextCriteria textCriteria = TextCriteria
            .forDefaultLanguage()
            .matchingPhrase(keyword);
        Query query = TextQuery.queryText(textCriteria).sortByScore();

        final List<Article> articles = new ArrayList<>(
            mongoTemplate.find(query, Article.class)
        );

        return articles
            .stream()
            .limit(limit.orElse(ArticleApiConstants.DEFAULT_LIMIT_ARTICLES.getArticlesLimit()))
            .toList();
    }

    public long countArticles() {
        return articleRepository.count();
    }

}
