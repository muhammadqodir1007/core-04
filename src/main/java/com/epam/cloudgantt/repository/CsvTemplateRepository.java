package com.epam.cloudgantt.repository;

import com.epam.cloudgantt.entity.CsvTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CsvTemplateRepository extends JpaRepository<CsvTemplate, Integer> {
    
}
