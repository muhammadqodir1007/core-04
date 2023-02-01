package com.epam.cloudgantt.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private UUID id;

    private Long taskNumber;

    private String sectionName;

    private String taskName;

    private String description;

    private LocalDateTime beginDate;

    private LocalDateTime endDate;

    private int duration;

    private String assignee;

    private UUID projectId;
}
