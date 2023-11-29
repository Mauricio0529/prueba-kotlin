package com.practica.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = Users.TABLE_NAME)
data class Users(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN_NAME)
    val id: Long = 0,

    @Column(name = USER_NAME_COLUMN_NAME)
    val userName: String? = null,

    @Column(name = NAME_BY_COLUMN_NAME)
    val name: String? = null,

    @Column(name = LAST_NAME_BY_COLUMN_NAME)
    val lasName: String? = null,

    @Column(name = PASSWORD_COLUMN_NAME)
    val password: String? = null,

    @Column(name = DATA_REGISTRATION_COLUMN_NAME)
    var dateRegistration: LocalDateTime? = null,

    @OneToMany(mappedBy = "users", cascade = [CascadeType.ALL])
    val methodPaymentList: List<MethodPayment> = emptyList(),

    @OneToMany(mappedBy = "users", cascade = [CascadeType.ALL])
    val rentalList: List<Rental> = emptyList(),

    ) {
    companion object {
        const val TABLE_NAME = "PERSONAS"
        const val ID_COLUMN_NAME = "ID"
        const val USER_NAME_COLUMN_NAME = "USER_NAME"
        const val NAME_BY_COLUMN_NAME = "NAME"
        const val LAST_NAME_BY_COLUMN_NAME = "LAST_NAME"
        const val PASSWORD_COLUMN_NAME = "PASSWORD"
        const val DATA_REGISTRATION_COLUMN_NAME = "DATA_REGISTRATION"
    }
}