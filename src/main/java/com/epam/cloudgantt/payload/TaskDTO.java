package com.epam.cloudgantt.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String beginDate;
    private int duration;
    private String endDate;
    private String assignee;
    private UUID project_id;
}
