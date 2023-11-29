package com.practica.service

import com.practica.exception.NumberCardExistException
import com.practica.exception.ValidatedNumberCard
import com.practica.model.MethodPayment
import com.practica.model.Rental
import com.practica.repository.MethodPaymentRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class MethodPaymentService constructor(
    val methodPaymentRepository: MethodPaymentRepository
){
    companion object {
        private const val CONST_TYPE_PAYMENT_CASH: String = "Efectivo"
    }

    fun getAll(): List<MethodPayment> {
        return methodPaymentRepository.findAll()
    }

    fun getById(methodPaymentId: Long): MethodPayment? {
        val methodPaymentOptional: Optional<MethodPayment> = methodPaymentRepository.findById(methodPaymentId)
        if(methodPaymentOptional.isEmpty) {
            return null
        }
        return methodPaymentOptional.get()
    }

    fun getByUsersId(usersId: Long): List<MethodPayment> {
        val methodPaymentList: List<MethodPayment> = methodPaymentRepository.findByUsersId(usersId)
        if(methodPaymentList.isEmpty()) {
            return emptyList()
        }
        return methodPaymentList
    }

    fun save(methodPayment: MethodPayment): MethodPayment {
        if(methodPayment.typePayment.equals(CONST_TYPE_PAYMENT_CASH)) {
            methodPayment.numberCard = 0
        }

        val numberCardRepeated: Int = methodPaymentRepository.countByNumberCard(methodPayment.numberCard!!)

        if(numberCardRepeated != 0) {
            throw NumberCardExistException("El numero de tarjeta ya existe.")
        }

        if(methodPayment.numberCard == null){
            throw ValidatedNumberCard("Por favor ingrese el numero de la tarjeta.")
        }

        return methodPaymentRepository.save(methodPayment)
    }

    fun updateByUsers(methodPayment: MethodPayment): MethodPayment? {
        val methodPaymentList: List<MethodPayment> = getByUsersId(methodPayment.usersId!!)

        if(methodPaymentList.isEmpty()) {
            null
        }

        if(methodPayment.typePayment.equals(CONST_TYPE_PAYMENT_CASH)) {
            methodPayment.numberCard = 0
        }
        methodPaymentList.stream().filter{ it.id == methodPayment.id }.forEach{ it ->
            if(methodPayment.numberCard == null) {
                methodPayment.numberCard = it.numberCard
            }

            if(methodPayment.typePayment == null) {
                methodPayment.typePayment = it.typePayment
            }
         }

        return methodPaymentRepository.save(methodPayment)
    }

    fun deleteByUser(methodPaymentId: Long, usersId: Long): Boolean {
        val methodPaymentList: List<MethodPayment> = getByUsersId(usersId)
        if(methodPaymentList.isEmpty()) {
            return false
        }

        for (methodPayment in methodPaymentList) {
            if(methodPayment.id == methodPaymentId) {
                methodPaymentRepository.deleteById(methodPaymentId)
                break
            }
        }
        return true
    }
}