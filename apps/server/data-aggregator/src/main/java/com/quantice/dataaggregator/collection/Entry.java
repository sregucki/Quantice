package com.quantice.dataaggregator.collection;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Document(collation = "entry")
public class Entry {

    @Id
    private String id;
    private String url;
    private String title;
    private String description;
    private String author;
    private String content;
    private Instant publishedAt;
    private String rssChannelId;

}
