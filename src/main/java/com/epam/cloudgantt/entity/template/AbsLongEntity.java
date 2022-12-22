package com.epam.cloudgantt.entity.template;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@MappedSuperclass
@Getter
public abstract class AbsLongEntity extends AbsAuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
