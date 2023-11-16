package com.example.hateoas.dao.bean;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Avatar {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, length = 36000)
    private byte[] data;
}
