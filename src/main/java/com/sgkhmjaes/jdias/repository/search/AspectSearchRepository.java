package com.sgkhmjaes.jdias.repository.search;

import com.sgkhmjaes.jdias.domain.Aspect;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Aspect entity.
 */
public interface AspectSearchRepository extends ElasticsearchRepository<Aspect, Long> {
}
