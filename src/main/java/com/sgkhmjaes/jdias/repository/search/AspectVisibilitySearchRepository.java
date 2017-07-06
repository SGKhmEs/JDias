package com.sgkhmjaes.jdias.repository.search;

import com.sgkhmjaes.jdias.domain.Aspectvisibility;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Aspectvisibility entity.
 */
public interface AspectvisibilitySearchRepository extends ElasticsearchRepository<Aspectvisibility, Long> {
}
