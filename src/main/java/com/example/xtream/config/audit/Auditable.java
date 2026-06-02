package com.example.xtream.config.audit;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 * @MappedSuperclass: allow entity extend for their field
 * @EntityListeners(AuditingEntityListener.class): config jpa automatically trigger insert,update
 * <p>
 * any ENTITY want automatically audit insert,update by JPA
 * just EXTEND this
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

    /**
     * automatically update by JPA when insert
     */
    @CreatedDate
    protected LocalDateTime createdAt;

    /**
     * automatically update by JPA when update
     */
    @LastModifiedDate
    protected LocalDateTime updatedAt;

}
