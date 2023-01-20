package com.epam.cloudgantt.entity;

import com.epam.cloudgantt.entity.template.AbsUUIDEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "projects")
@Getter
@Setter
@Accessors(chain = true)
public class Project extends AbsUUIDEntity {

    @Column(nullable = false)
    private String name;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;
}
