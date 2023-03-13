package com.quantice.dataaggregator.model;


import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.TextScore;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Document(collection = "entry")
public class Entry {

    @Id
    private String id;
    @Indexed(unique = true)
    @TextIndexed
    private String url;
    @TextIndexed
    private String title;
    @TextIndexed
    private String description;
    @TextScore
    public Float score;
    private Instant publishedAt;

}
