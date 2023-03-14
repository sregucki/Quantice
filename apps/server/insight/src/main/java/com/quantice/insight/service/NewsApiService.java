package com.quantice.insight.service;

import com.quantice.insight.config.ArticleApiConstants;
import com.quantice.insight.config.properties.NewsApiProperties;
import com.quantice.insight.model.Article;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Deprecated
public class NewsApiService {

    private final NewsApiProperties newsApiProperties;

    public List<Article> findArticles(final String keyword, final Optional<String> from, final Optional<String> to, final Optional<Integer> limit) {

        final RestTemplate restTemplate = new RestTemplate();
        final String url = String.format(
            "https://newsapi.org/v2/everything?q=%s&from=%s&to=%s&sortBy=publishedAt&pageSize=%s&language=en&apiKey=%s",
            keyword,
            from.orElse(Instant.now().minus(7, ChronoUnit.DAYS).toString()),
            to.orElse(Instant.now().toString()),
            limit.orElse(ArticleApiConstants.DEFAULT_LIMIT_ARTICLES.getArticlesLimit()),
            newsApiProperties.getNewsApiKey()
        );

        Map response = restTemplate.getForObject(url, Map.class);
        List<Map> apiArticles = (List<Map>) response.get("articles");

        return apiArticles.stream().map(apiArticle -> Article.builder()
            .url(String.valueOf(apiArticle.get("url")))
            .title(String.valueOf(apiArticle.get("title")))
            .publishedAt(Instant.parse(String.valueOf(apiArticle.get("publishedAt"))))
            .description(String.valueOf(apiArticle.get("description")))
            .build()
        ).toList();
    }

}
