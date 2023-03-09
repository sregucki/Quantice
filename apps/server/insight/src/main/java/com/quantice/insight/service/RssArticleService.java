package com.quantice.insight.service;

import static java.util.stream.Collectors.groupingBy;

import com.quantice.insight.config.ArticleApiConstants;
import com.quantice.insight.model.Article;
import com.quantice.insight.repository.ArticleRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RssArticleService {

    private final ArticleRepository articleRepository;

    public List<Article> findArticles(final String keyword, final Optional<String> from, final Optional<String> to, final Optional<Integer> limit) {

        Instant fromInstant;
        Instant toInstant;

        if (from.isPresent()) {
            fromInstant = Instant.parse(from.get());
        }
        else {
            fromInstant = Instant.now().minus(7, ChronoUnit.DAYS);
        }

        if (to.isPresent()) {
            toInstant = Instant.parse(to.get());
        }
        else {
            toInstant = Instant.now();
        }

        final List<Article> articles = new ArrayList<>();
        articles.addAll(articleRepository.findTop100ByUrlContainingIgnoreCaseAndPublishedAtBetweenOrderByPublishedAt(keyword, fromInstant, toInstant));
        articles.addAll(articleRepository.findTop100ByTitleContainingIgnoreCaseAndPublishedAtBetweenOrderByPublishedAt(keyword, fromInstant, toInstant));
        articles.addAll(articleRepository.findTop100ByDescriptionContainingIgnoreCaseAndPublishedAtBetweenOrderByPublishedAt(keyword, fromInstant, toInstant));

        return articles
            .stream()
            .collect(groupingBy(Article::getUrl))
            .values()
            .stream()
            .flatMap(values -> values.stream().limit(1))
            .collect(groupingBy(Article::getTitle))
            .values()
            .stream()
            .flatMap(values -> values.stream().limit(1))
            .limit(limit.orElse(ArticleApiConstants.DEFAULT_LIMIT_ARTICLES.getArticlesLimit()))
            .toList();
    }

}
