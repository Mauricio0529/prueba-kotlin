package com.practica.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = Rental.TABLE_NAME)
data class Rental(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN_NAME)
    val id: Long = 0,

    @Column(name = USER_ID_COLUMN_NAME)
    val usersId: Long? = null,

    @Column(name = METHOD_PAYMENT_ID_COLUMN_NAME)
    val methodPaymentId: Long? = null,

    @Column(name = VALUE_RENTAL_COLUMN_NAME)
    val valueRental: Double? = null,

    @Column(name = DATE_START_COLUMN_NAME)
    var dateStart: LocalDateTime? = null,

    @Column(name = DATE_FINALIZED_COLUMN_NAME)
    var dateFinalized: LocalDateTime? = null,

    @Column(name = STATUS_COLUMN_NAME)
    var status: String? = null,

    @ManyToOne()
    @JoinColumn(name = "methodPaymentId", insertable = false, updatable = false)
    val methodPaymentEntity: MethodPayment? = null,

    @ManyToOne()
    @JoinColumn(name = "usersId", insertable = false, updatable = false)
    val users: Users? = null,

    @OneToMany(mappedBy = "rental", cascade = [CascadeType.ALL])
    val vehiclesList: List<Vehicle> = emptyList()

){
    companion object {
        const val TABLE_NAME = "RENTAL"
        const val ID_COLUMN_NAME = "ID_COLUMN_NAME"
        const val USER_ID_COLUMN_NAME = "usersId"
        const val METHOD_PAYMENT_ID_COLUMN_NAME = "methodPaymentId"
        const val VALUE_RENTAL_COLUMN_NAME = "VALUE_RENTAL"
        const val DATE_START_COLUMN_NAME = "DATE_START"
        const val DATE_FINALIZED_COLUMN_NAME = "DATE_FINALIZED"
        const val STATUS_COLUMN_NAME = "STATUS"
    }
}