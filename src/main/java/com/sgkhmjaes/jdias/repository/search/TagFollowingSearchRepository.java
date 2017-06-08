package com.sgkhmjaes.jdias.repository.search;

import com.sgkhmjaes.jdias.domain.TagFollowing;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TagFollowing entity.
 */
public interface TagFollowingSearchRepository extends ElasticsearchRepository<TagFollowing, Long> {
}
