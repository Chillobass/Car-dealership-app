package org.example.data.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.example.data.jpa.domainEntities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> { }
