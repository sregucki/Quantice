package com.quantice.dataaggregator.repository;

import com.quantice.dataaggregator.model.Channel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChannelRepository extends MongoRepository<Channel, String> {

}
