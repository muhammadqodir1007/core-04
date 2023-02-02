package com.epam.cloudgantt.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SectionDTO {

    private String name;

    private List<TaskDTO> tasks;
}
