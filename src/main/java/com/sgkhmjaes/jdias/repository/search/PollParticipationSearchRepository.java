package com.sgkhmjaes.jdias.repository.search;

import com.sgkhmjaes.jdias.domain.PollParticipation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PollParticipation entity.
 */
public interface PollParticipationSearchRepository extends ElasticsearchRepository<PollParticipation, Long> {
}
