package com.epam.cloudgantt.repository;

import com.epam.cloudgantt.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    @Query(value = "SELECT * FROM TASKS WHERE PROJECT_ID = ?1 ORDER BY TASK_NUMBER ASC",
            nativeQuery = true)
    Page<Task> findByProjectId(UUID projectId, Pageable pageable);

    Task getAllByTaskNumber(Long taskNumber);

    List<Task> getAllByDependencyContaining(String taskNumber);

}
