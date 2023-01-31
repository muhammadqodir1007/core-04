package com.epam.cloudgantt.payload;

import com.epam.cloudgantt.entity.Project;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class TaskDto {
    private Long taskNumber;
    private String sectionName;
    private String taskName;
    private String description;
    private String beginDate;
    private String  endDate;
    private String assignee;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
}
