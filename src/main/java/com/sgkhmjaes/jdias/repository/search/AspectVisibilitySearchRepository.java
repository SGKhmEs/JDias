package com.sgkhmjaes.jdias.repository.search;

import com.sgkhmjaes.jdias.domain.AspectVisibility;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AspectVisibility entity.
 */
public interface AspectVisibilitySearchRepository extends ElasticsearchRepository<AspectVisibility, Long> {
}
