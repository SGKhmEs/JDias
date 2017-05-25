package com.sgkhmjaes.jdias.repository.search;

import com.sgkhmjaes.jdias.domain.EventParticipation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EventParticipation entity.
 */
public interface EventParticipationSearchRepository extends ElasticsearchRepository<EventParticipation, Long> {
}
