package com.epam.cloudgantt.entity;

import com.epam.cloudgantt.entity.template.AbsUUIDEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@Accessors(chain = true)
public class Task extends AbsUUIDEntity {

    private Long taskNumber;
    private String taskName;
    private String sectionName;
    private String description;
    private String beginDate;
    private String endDate;
    private String assignee;
    private boolean exceededTextAlert;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
}