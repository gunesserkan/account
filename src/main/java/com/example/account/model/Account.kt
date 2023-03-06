package com.example.account.model

import jakarta.persistence.*
import org.hibernate.Transaction
import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.time.LocalDateTime


@Entity
data class Account(

        @Id
        @GeneratedValue(generator="UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDHexGenerator")
        val id:String?,
        val balance: BigDecimal? = BigDecimal.ZERO,
        val creationDate: LocalDateTime,

        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "customer_id",nullable = false)
        val customer: Customer?,

        @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
        val transaction: Set<Transaction>?
) {

}
