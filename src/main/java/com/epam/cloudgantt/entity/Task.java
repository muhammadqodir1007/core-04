package com.epam.cloudgantt.entity;

import com.epam.cloudgantt.entity.template.AbsUUIDEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@Accessors(chain = true)
public class Task extends AbsUUIDEntity {

    @Column(nullable = false, name = "task_number")
    private Long taskNumber;

    @Column(nullable = false, name = "task_name")
    private String taskName;

    @Column(name = "section_name")
    private String sectionName;

    private String description;

    @Column(name = "begin_date")
    private LocalDateTime beginDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    private String assignee;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id")
    private Project project;
}