package com.practica.controller

import com.practica.dto.VehicleDto
import com.practica.dto.mapper.VehicleMapper
import com.practica.model.Vehicle
import com.practica.service.VehicleService
import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/vehicle")
class VehicleController @Autowired constructor(
    val vehicleService: VehicleService,
    val mapper: VehicleMapper
){

    @GetMapping("/list")
    //@ApiResponse(responseCode = "200", description = "Lista de Vehículos")
    fun getAll(): ResponseEntity<Any> {
        val vehicleList: List<Vehicle> = vehicleService.getAll()

        return try {
            if(vehicleList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            }
            /**
             * Mapear una lista de entidad a dto
             */
            ResponseEntity.status(HttpStatus.OK).body(vehicleList.map { mapper.fromEntityToDto(it) })
        } catch (responseStatusException: ResponseStatusException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf(responseStatusException.message))
        }
    }

    @GetMapping("/get-id")
    fun getById(@RequestParam vehicleId: Long): ResponseEntity<Any> {
        return ResponseEntity.of(vehicleService.getById(vehicleId).map { mapper.fromEntityToDto(it) })
    }

    @PostMapping("/create")
    fun save(@RequestBody vehicleDto: VehicleDto): ResponseEntity<Any> {
        return try {
            if(vehicleDto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            }
            ResponseEntity.status(HttpStatus.CREATED).body(mapper.fromEntityToDto(vehicleService.save(vehicleDto)))
        } catch (ex: EntityNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        } catch (responseStatusException: ResponseStatusException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf(responseStatusException.message))
        }
    }

    @DeleteMapping("/delete")
    fun delete(@RequestParam vehicleId: Long): ResponseEntity<Any> {
        return try {
            ResponseEntity.status(HttpStatus.OK).body(vehicleService.delete(vehicleId))
        } catch (ex: EntityNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        } catch (responseStatusException: ResponseStatusException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf(responseStatusException.message))
        }
    }
}