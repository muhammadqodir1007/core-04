package com.epam.cloudgantt.repository;

import com.epam.cloudgantt.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

    Optional<Project> findByName(String name);

    boolean existsByName(String name);
}
