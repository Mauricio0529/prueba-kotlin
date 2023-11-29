package com.practica.service

import com.practica.dto.VehicleDto
import com.practica.dto.mapper.VehicleMapper
import com.practica.model.Vehicle
import com.practica.repository.VehicleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class VehicleService  @Autowired constructor(
    val vehicleRepository: VehicleRepository,
    val mapper: VehicleMapper
){

    fun getAll():List<Vehicle> {
        return vehicleRepository.findAll()
    }

   fun getById(vehicleId: Long): Optional<Vehicle> {
       return Optional.of(vehicleRepository.findById(vehicleId).get())
   }

   fun save(vehicleDto: VehicleDto): Vehicle {
       return vehicleRepository.save(mapper.fromDtoToEntity(vehicleDto))
   }

   fun delete(vehicleId: Long): Boolean {
       if(getById(vehicleId).isEmpty) {
           return false
       }
       vehicleRepository.deleteById(vehicleId)
       return true
   }
}