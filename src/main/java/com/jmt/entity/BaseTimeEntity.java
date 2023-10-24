package com.jmt.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class})
@Data
@MappedSuperclass
public abstract class BaseTimeEntity {

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime regDate;

    @LastModifiedDate
    private LocalDateTime modDate;
}
