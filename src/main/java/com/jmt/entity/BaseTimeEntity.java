package com.jmt.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    protected BaseTimeEntity() {
        this.regDate = LocalDateTime.now();
        this.modDate = LocalDateTime.now(); // modDate 필드에도 초기값 설정
    }

    // modDate를 업데이트하는 메소드
    public void updateModDate() {
        this.modDate = LocalDateTime.now();
    }
}

