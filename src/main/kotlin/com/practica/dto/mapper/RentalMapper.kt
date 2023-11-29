package com.practica.dto.mapper

import com.practica.dto.RentalDto
import com.practica.dto.UsersDto
import com.practica.model.Rental
import com.practica.model.Users
import org.springframework.stereotype.Component

@Component
class RentalMapper (
    val vehicleMapper: VehicleMapper
){

    fun fromDtoToEntity(rentalDto: RentalDto): Rental {
        /**
         * Mapear una lista de DTO a Entity
         */
        val vehiclesList = rentalDto.vehiclesList.map { vehicleMapper.fromDtoToEntity(it) }
        return Rental(
            id = rentalDto.id,
            usersId = rentalDto.usersId,
            methodPaymentId = rentalDto.methodPaymentId,
            valueRental = rentalDto.valueRental,
            dateStart = rentalDto.dateStart,
            dateFinalized = rentalDto.dateFinalized,
            status = rentalDto.status,
            vehiclesList = vehiclesList
        )
    }


    fun fromEntityToDto(rental: Rental): RentalDto {
        /**
         * Mapear una lista de DTO a Entity
         */
        val vehiclesList = rental.vehiclesList.map { vehicleMapper.fromEntityToDto(it) }
        return RentalDto(
            id = rental.id,
            usersId = rental.usersId,
            methodPaymentId = rental.methodPaymentId,
            valueRental = rental.valueRental,
            dateStart = rental.dateStart,
            dateFinalized = rental.dateFinalized,
            status = rental.status,
            vehiclesList = vehiclesList
        )
    }
}