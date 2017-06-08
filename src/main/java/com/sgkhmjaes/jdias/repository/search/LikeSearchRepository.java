package com.sgkhmjaes.jdias.repository.search;

import com.sgkhmjaes.jdias.domain.Like;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Like entity.
 */
public interface LikeSearchRepository extends ElasticsearchRepository<Like, Long> {
}
