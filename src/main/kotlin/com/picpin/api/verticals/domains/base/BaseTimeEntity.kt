package com.picpin.api.verticals.domains.base

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseTimeEntity {

    @CreationTimestamp
    @Column(name = "created_datetime", nullable = false, updatable = false)
    open var createdDatetime: LocalDateTime? = null

    @UpdateTimestamp
    @Column(name = "updated_datetime")
    open var updatedDatetime: LocalDateTime? = null

    @PrePersist
    fun onPrePersist() {
        createdDatetime = LocalDateTime.now(DEFAULT_ZONE_ID)
    }

    @PreUpdate
    fun onPreUpdate() {
        updatedDatetime = LocalDateTime.now(DEFAULT_ZONE_ID)
    }
}
