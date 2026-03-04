package com.teample.matching.common;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 1. JPA 엔티티들이 이 클래스를 상속받을 경우 필드들을 컬럼으로 인식하게 합니다.
@EntityListeners(AuditingEntityListener.class) // 2. 이 클래스에 Auditing 기능을 포함시킵니다.
public abstract class BaseTimeEntity {

    @CreatedDate // 3. 엔티티가 생성되어 저장될 때 시간이 자동 저장됩니다.
    private LocalDateTime createdAt;

    @LastModifiedDate // 4. 조회한 엔티티의 값을 변경할 때 시간이 자동 저장됩니다.
    private LocalDateTime modifiedAt;
}