package com.practica.model

import jakarta.persistence.*

@Entity
@Table(name = MethodPayment.TABLE_NAME)
data class MethodPayment(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN_NAME)
    val id: Long = 0,

    @Column(name = USER_ID_COLUMN_NAME)
    var usersId: Long? = null,

    @Column(name = TYPE_PAYMENT_COLUMN_NAME)
    var typePayment: String? = null,

    @Column(name = NUMBER_CARD_COLUMN_NAME)
    var numberCard: Int? = null,

    @ManyToOne()
    @JoinColumn(name = "usersId", insertable = false, updatable = false)
    val users: Users? = null

) {
    companion object {
        const val TABLE_NAME = "METHOD_PAYMENT"
        const val ID_COLUMN_NAME = "ID_COLUMN_NAME"
        const val USER_ID_COLUMN_NAME = "usersId"
        const val TYPE_PAYMENT_COLUMN_NAME = "TYPE_PAYMENT"
        const val NUMBER_CARD_COLUMN_NAME = "NUMBER_CARD"
    }
}