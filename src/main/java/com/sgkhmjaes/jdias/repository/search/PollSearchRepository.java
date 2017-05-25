package com.sgkhmjaes.jdias.repository.search;

import com.sgkhmjaes.jdias.domain.Poll;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Poll entity.
 */
public interface PollSearchRepository extends ElasticsearchRepository<Poll, Long> {
}
