package fci.swe.advanced_software.repositories;

import fci.swe.advanced_software.models.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AbstractEntityRepository<T extends AbstractEntity> extends JpaRepository<T, String> {
}
