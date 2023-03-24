package com.quantice.dataaggregator.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Document(collection = "channel")
public class Channel {

    @Id
    private String id;
    @Indexed(unique = true)
    private String url;
    private Language language;
    private Topic topic;
    private Instant lastParsingAttempt;
    @CreatedDate
    private Instant createdAt;

}
