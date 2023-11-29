package com.practica.repository

import com.practica.model.MethodPayment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface MethodPaymentRepository: JpaRepository<MethodPayment, Long> {
    fun findByUsersId(userId: Long?): List<MethodPayment>

    //@Query(value = "SELECT * FROM method_payment WHERE number_card = :numberCard", nativeQuery = true)
    fun countByNumberCard(numberCard: Int): Int
}