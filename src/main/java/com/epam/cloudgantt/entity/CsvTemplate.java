package com.epam.cloudgantt.entity;

import com.epam.cloudgantt.entity.template.AbsIntegerEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "csv_template")
@Getter
@Setter
public class CsvTemplate extends AbsIntegerEntity {
    @Column(nullable = false, unique = true)
    String nameOfColumn;
}
