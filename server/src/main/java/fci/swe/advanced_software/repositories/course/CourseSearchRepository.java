package fci.swe.advanced_software.repositories.course;

import fci.swe.advanced_software.dtos.course.CourseSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CourseSearchRepository extends ElasticsearchRepository<CourseSearchDto, String> {
    Page<CourseSearchDto> findByCodeOrNameOrDescription(String code, String name, String description, Pageable pageable);

    Page<CourseSearchDto> searchAllByCodeOrNameOrDescription(String code, String name, String description, Pageable pageable);
}