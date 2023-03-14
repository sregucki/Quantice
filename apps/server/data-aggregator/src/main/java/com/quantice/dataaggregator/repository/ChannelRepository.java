package com.quantice.dataaggregator.repository;

import com.quantice.dataaggregator.model.Channel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ChannelRepository extends ReactiveMongoRepository<Channel, String> {

}
