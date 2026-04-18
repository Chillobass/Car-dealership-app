package ru.Burunsuzian.data.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.Burunsuzian.data.jpa.domainEntities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> { }
