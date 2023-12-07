package com.sparta.backoffice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class TimeStamped {

    @CreatedDate
    @Column(updatable = false)          // update 가 되지 않게 막아준다.
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate                   // 변경이 생길때 마다 변경시간을 적용
    @Column
    @Temporal(TemporalType.TIMESTAMP)   // Date나 Calendar 의 날짜데이터를 맵핑할 때 사용함
    private LocalDateTime modifiedAt;

    // DATE : 2023-01-01
    // TIME : 20:21:14
    // TIMESTAMP : 2023-01-01 20:21:14.9989889
}