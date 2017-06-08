package com.sgkhmjaes.jdias.repository.search;

import com.sgkhmjaes.jdias.domain.AspectMembership;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AspectMembership entity.
 */
public interface AspectMembershipSearchRepository extends ElasticsearchRepository<AspectMembership, Long> {
}
