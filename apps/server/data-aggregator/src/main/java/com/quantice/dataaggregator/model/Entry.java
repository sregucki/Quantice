package com.quantice.dataaggregator.model;


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
@Document(collation = "entry")
public class Entry {

    @Id
    private String id;
    // TODO unique url saving
    private String url;
    private String title;
    private String description;
    private String author;
    private String content;
    private Instant publishedAt;
    private String rssChannelId;

}
