package com.quantice.insight.repository;

import com.quantice.insight.model.Article;
import java.time.Instant;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<Article, String> {

    List<Article> findTop100ByDescriptionContainingIgnoreCaseAndPublishedAtBetweenOrderByPublishedAt(String keyword, Instant from, Instant to);

    List<Article> findTop100ByTitleContainingIgnoreCaseAndPublishedAtBetweenOrderByPublishedAt(String keyword, Instant from, Instant to);

    List<Article> findTop100ByUrlContainingIgnoreCaseAndPublishedAtBetweenOrderByPublishedAt(String keyword, Instant from, Instant to);

}
