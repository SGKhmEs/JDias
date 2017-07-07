package com.sgkhmjaes.jdias.repository.search;

import com.sgkhmjaes.jdias.domain.HashTag;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the HashTag entity.
 */
public interface HashTagSearchRepository extends ElasticsearchRepository<HashTag, Long> {
}
