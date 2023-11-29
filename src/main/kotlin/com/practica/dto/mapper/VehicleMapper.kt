package com.practica.dto.mapper

import com.practica.dto.VehicleDto
import com.practica.model.Vehicle
import org.springframework.stereotype.Component

@Component
class VehicleMapper {

    /**
     * Mapea un objeto VehicleDto a una VehicleEntity
     */
    fun fromDtoToEntity(vehicleDto: VehicleDto): Vehicle {
        return Vehicle(
            id = vehicleDto.id,
            rentalId = vehicleDto.rentalId,
            nameVehicle = vehicleDto.nameVehicle,
            modelYear = vehicleDto.modelYear
        )
    }

    /**
     * Mapea un objeto VehicleEntity a un VehicleDto
     */
    fun fromEntityToDto(vehicle: Vehicle): VehicleDto {
        return VehicleDto(
            id = vehicle.id,
            rentalId = vehicle.rentalId,
            nameVehicle = vehicle.nameVehicle,
            modelYear = vehicle.modelYear
        )
    }
}