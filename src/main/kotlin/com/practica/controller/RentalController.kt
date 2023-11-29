package com.practica.controller

import com.practica.dto.mapper.RentalMapper
import com.practica.exception.MethodPaymentToUserNotExistException
import com.practica.exception.NumberCardExistException
import com.practica.exception.UserNameAlreadyExistsException
import com.practica.model.Rental
import com.practica.model.Users
import com.practica.service.RentalService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/rental")
class RentalController constructor(
    val rentalService: RentalService,
    val rentalMapper: RentalMapper
){

    @GetMapping("/list")
    fun getAll(): ResponseEntity<Any> {
        return try {
            val rentalList: List<Rental> = rentalService.getAll()
            if(rentalList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            }
            ResponseEntity.status(HttpStatus.OK).body(rentalList.map { rentalMapper.fromEntityToDto(it) })
        } catch (responseStatusException: ResponseStatusException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf(responseStatusException.message))
        }
    }

    @GetMapping("/get-id")
    fun getById(@RequestParam rentalId: Long): ResponseEntity<Any> {
        return try {
            val rental: Rental? = rentalService.getById(rentalId)
            if(rental == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            }
            return ResponseEntity.status(HttpStatus.OK).body(rentalMapper.fromEntityToDto(rental))
        } catch (responseStatusException: ResponseStatusException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf(responseStatusException.message))
        }
    }

    @GetMapping("/get-userId")
    fun getByUserId(@RequestParam usersId: Long): ResponseEntity<Any> {
        val rental: Rental? = rentalService.getByUsersId(usersId)
        return try {
            if(rental == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            }
            return ResponseEntity.status(HttpStatus.OK).body(rentalMapper.fromEntityToDto(rental))
        } catch (responseStatusException: ResponseStatusException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf(responseStatusException.message))
        }
    }

    @PostMapping("/create")
    fun save(@RequestBody rental: Rental): ResponseEntity<Any> {
        return try {
            ResponseEntity.status(HttpStatus.CREATED).body(rentalMapper.fromEntityToDto(rentalService.save(rental)))

        } catch (methodException: MethodPaymentToUserNotExistException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(methodException)

        } catch (responseStatusException: ResponseStatusException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf(responseStatusException.message))
        }
    }

    @PatchMapping("/update-status")
    fun update(@RequestParam rentalId: Long, @RequestParam newStatus: String): ResponseEntity<Any> {
        return try {
            ResponseEntity.status(HttpStatus.OK).body(rentalMapper.fromEntityToDto(rentalService.updateStatus(rentalId, newStatus)!!))
        } catch (responseStatusException: ResponseStatusException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf(responseStatusException.message))
        }
    }
}