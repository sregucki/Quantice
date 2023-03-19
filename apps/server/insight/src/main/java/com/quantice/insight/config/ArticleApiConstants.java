package com.quantice.insight.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ArticleApiConstants {
    DEFAULT_LIMIT_ARTICLES(50);

    private final int articlesLimit;

}
