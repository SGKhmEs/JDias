package com.sgkhmjaes.jdias.repository.search;

import com.sgkhmjaes.jdias.domain.StatusMessage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the StatusMessage entity.
 */
public interface StatusMessageSearchRepository extends ElasticsearchRepository<StatusMessage, Long> {
}
