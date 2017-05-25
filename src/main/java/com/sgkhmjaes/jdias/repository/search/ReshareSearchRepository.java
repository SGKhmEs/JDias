package com.sgkhmjaes.jdias.repository.search;

import com.sgkhmjaes.jdias.domain.Reshare;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Reshare entity.
 */
public interface ReshareSearchRepository extends ElasticsearchRepository<Reshare, Long> {
}
