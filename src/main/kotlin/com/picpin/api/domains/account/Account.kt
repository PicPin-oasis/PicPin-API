package com.picpin.api.domains.account

import com.picpin.api.domains.base.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(
    indexes = [
        Index(name = "idx_account_vendor_id", columnList = "vendor_id")
    ]
)
class Account(
    @Column(nullable = false, unique = true, name = "vendor_id")
    val vendorId: Long,
    @Column(nullable = false, length = 255)
    val username: String,
    @Column(nullable = false, name = "profile_image_url", length = 255)
    val profileImageUrl: String,
    @Column(nullable = true, length = 255)
    val email: String? = null,
    @Column(nullable = true)
    val birthday: LocalDate? = null,
    @Column(nullable = true)
    val gender: String? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : BaseTimeEntity() {

    companion object {
        fun fixture(): Account = Account(
            1,
            "name",
            "profile_image_url",
            "email",
            LocalDate.now(),
            "MALE",
            1
        )
    }
}
