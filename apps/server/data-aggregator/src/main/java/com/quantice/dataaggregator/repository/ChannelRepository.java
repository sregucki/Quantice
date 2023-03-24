package com.quantice.dataaggregator.repository;

import com.quantice.dataaggregator.model.Channel;
import com.quantice.dataaggregator.model.Language;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChannelRepository extends MongoRepository<Channel, String> {

    Channel findByUrl(String url);

    List<Channel> findAllByLanguage(Language language);

}
