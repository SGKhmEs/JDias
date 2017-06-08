package com.sgkhmjaes.jdias.repository.search;

import com.sgkhmjaes.jdias.domain.Message;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Message entity.
 */
public interface MessageSearchRepository extends ElasticsearchRepository<Message, Long> {
}
