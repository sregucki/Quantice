package com.quantice.insight.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class Article {

    private String url;
    private String title;
    private String description;
    private String author;
    private Instant publishedAt;

}
