package com.quantice.dataaggregator.repository;


import com.quantice.dataaggregator.model.Entry;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EntryRepository extends ReactiveMongoRepository<Entry, String> {

}
