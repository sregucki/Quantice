package com.quantice.insight.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Document(collection = "entry")
public class Article {

    @Id
    private String id;
    private String url;
    private String title;
    private String description;
    private String author;
    private Instant publishedAt;

}
