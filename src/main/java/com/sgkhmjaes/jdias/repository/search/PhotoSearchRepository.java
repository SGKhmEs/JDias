package com.sgkhmjaes.jdias.repository.search;

import com.sgkhmjaes.jdias.domain.Photo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Photo entity.
 */
public interface PhotoSearchRepository extends ElasticsearchRepository<Photo, Long> {
}
