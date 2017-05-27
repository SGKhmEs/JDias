package com.sgkhmjaes.jdias.repository.search;

import com.sgkhmjaes.jdias.domain.AspectVisiblity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AspectVisiblity entity.
 */
public interface AspectVisiblitySearchRepository extends ElasticsearchRepository<AspectVisiblity, Long> {
}
