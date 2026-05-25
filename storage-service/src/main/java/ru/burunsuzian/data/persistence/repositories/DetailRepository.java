package ru.burunsuzian.data.persistence.repositories;

import ru.burunsuzian.data.jpa.replaceableEntities.ReplaceableComponentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface DetailRepository extends JpaRepository<ReplaceableComponentType, UUID> {
}