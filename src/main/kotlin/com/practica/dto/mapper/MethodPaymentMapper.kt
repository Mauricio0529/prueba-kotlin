package com.practica.dto.mapper

import com.practica.dto.MethodPaymentDto
import com.practica.model.MethodPayment
import org.springframework.stereotype.Component

@Component
class MethodPaymentMapper {

    fun fromDtoToEntity(methodPaymentDto: MethodPaymentDto): MethodPayment {
        return MethodPayment(
            id = methodPaymentDto.id,
            usersId = methodPaymentDto.usersId,
            typePayment = methodPaymentDto.typePayment,
            numberCard = methodPaymentDto.numberCard
        )
    }

    fun fromEntityToDto(methodPayment: MethodPayment): MethodPaymentDto {
        return MethodPaymentDto(
            id = methodPayment.id,
            usersId = methodPayment.usersId,
            typePayment = methodPayment.typePayment,
            numberCard = methodPayment.numberCard
        )
    }
}