package com.epam.cloudgantt.entity;

import com.epam.cloudgantt.entity.template.AbsUUIDEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "projects")
@Getter
@Setter
@Accessors(chain = true)
public class Project extends AbsUUIDEntity {

    @Column(nullable = false)
    private String name;

}
