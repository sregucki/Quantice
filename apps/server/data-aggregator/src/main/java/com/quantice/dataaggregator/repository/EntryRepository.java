package com.quantice.dataaggregator.repository;


import com.quantice.dataaggregator.model.Entry;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface EntryRepository extends ReactiveMongoRepository<Entry, String> {

    Mono<Boolean> existsByUrl(String url);

}
