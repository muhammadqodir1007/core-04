package com.epam.cloudgantt.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

    private UUID id;
    private String name;

    private List<SectionDTO> sections;

    private int totalPages;

    private boolean hasAssignee;

    private boolean hasDate;

    public ProjectDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
