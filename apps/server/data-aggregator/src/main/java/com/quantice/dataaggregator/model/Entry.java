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
    @TextIndexed(weight = 3)
    private String url;

    @TextIndexed(weight = 5)
    private String title;

    @TextIndexed(weight = 2)
    private String description;

    private Instant publishedAt;

    @TextScore
    private Float score;

}
