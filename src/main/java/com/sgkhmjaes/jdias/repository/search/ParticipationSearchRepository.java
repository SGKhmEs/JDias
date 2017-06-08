package com.sgkhmjaes.jdias.repository.search;

import com.sgkhmjaes.jdias.domain.Participation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Participation entity.
 */
public interface ParticipationSearchRepository extends ElasticsearchRepository<Participation, Long> {
}
