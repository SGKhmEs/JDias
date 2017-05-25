package com.sgkhmjaes.jdias.repository.search;

import com.sgkhmjaes.jdias.domain.AccountDeletion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AccountDeletion entity.
 */
public interface AccountDeletionSearchRepository extends ElasticsearchRepository<AccountDeletion, Long> {
}
