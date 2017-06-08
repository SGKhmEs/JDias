package com.sgkhmjaes.jdias.repository.search;

import com.sgkhmjaes.jdias.domain.Retraction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Retraction entity.
 */
public interface RetractionSearchRepository extends ElasticsearchRepository<Retraction, Long> {
}
