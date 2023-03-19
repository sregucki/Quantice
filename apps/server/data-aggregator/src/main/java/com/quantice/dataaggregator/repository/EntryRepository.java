package com.quantice.dataaggregator.repository;

import com.quantice.dataaggregator.model.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EntryRepository extends MongoRepository<Entry, String> {

    boolean existsByUrl(String url);

}
