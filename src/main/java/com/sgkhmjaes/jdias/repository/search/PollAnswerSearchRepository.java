package com.sgkhmjaes.jdias.repository.search;

import com.sgkhmjaes.jdias.domain.PollAnswer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PollAnswer entity.
 */
public interface PollAnswerSearchRepository extends ElasticsearchRepository<PollAnswer, Long> {
}
