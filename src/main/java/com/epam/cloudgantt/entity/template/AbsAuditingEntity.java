package com.epam.cloudgantt.entity.template;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.util.UUID;

@MappedSuperclass
@Getter
public abstract class AbsAuditingEntity extends AbsTimeEntity {

    @CreatedBy
    @Column(updatable = false)
    private UUID createdById;

    @LastModifiedBy
    private UUID updatedById;
}
