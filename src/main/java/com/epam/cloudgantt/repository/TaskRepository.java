package com.epam.cloudgantt.repository;

import com.epam.cloudgantt.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    @Query(value = "SELECT * FROM TASKS WHERE PROJECT_ID = ?1",
            nativeQuery = true)
    Page<Task> findByProjectId(UUID projectId, Pageable pageable);

}
