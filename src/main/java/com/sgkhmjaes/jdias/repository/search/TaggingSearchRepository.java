package com.sgkhmjaes.jdias.repository.search;

import com.sgkhmjaes.jdias.domain.Tagging;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Tagging entity.
 */
public interface TaggingSearchRepository extends ElasticsearchRepository<Tagging, Long> {
}
