package fci.swe.advanced_software.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RepositoryUtils {

    public Pageable getPageable(Integer page, Integer size, Sort.Direction direction, String... properties) {
        size = Math.min(size, 100);
        return PageRequest.of(page - 1, size, Sort.by(direction, properties));
    }
}
