package com.practica.dto.mapper

import com.practica.dto.UsersDto
import com.practica.model.Users
import org.springframework.stereotype.Component

@Component
class UsersMapper(
    val methodPaymentMapper: MethodPaymentMapper
) {

    /**
     * PASAR DE DTO A ENTITY
     */
    fun fromDtoToEntity(usersDto: UsersDto): Users {
        /**
         * Mapear una lista de DTO a Entity
         */
        val methodPayment = usersDto.methodPaymentList.map { methodPaymentMapper.fromDtoToEntity(it) }
        return Users(
            id = usersDto.id,
            userName = usersDto.userName,
            name = usersDto.name,
            lasName = usersDto.lasName,
            password = usersDto.password,
            dateRegistration = usersDto.dateRegistration,
            methodPaymentList =  methodPayment
        )
    }

    /**
     * PASAR DE ENTITY A DTO
     */
    fun fromEntityToDto(users: Users): UsersDto {
        /**
         * Mapear una lista de DTO a Entity
         */
        val methodPayment = users.methodPaymentList.map { methodPaymentMapper.fromEntityToDto(it) }
        return UsersDto(
            id = users.id,
            userName = users.userName,
            name = users.name,
            lasName = users.lasName,
            password = users.password,
            dateRegistration = users.dateRegistration,
            methodPaymentList = methodPayment
        )
    }

}