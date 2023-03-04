package com.quantice.insight.controller;

import com.quantice.insight.model.Article;
import com.quantice.insight.service.NewsApiService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final NewsApiService newsApiService;

    @QueryMapping
    public List<Article> findArticles(@Argument String keyword, @Argument String from, @Argument String to, @Argument Integer limit) {

        return newsApiService.findArticles(keyword, Optional.ofNullable(from), Optional.ofNullable(to), Optional.ofNullable(limit));
    }

}
