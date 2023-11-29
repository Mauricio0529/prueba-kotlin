package com.practica.controller

import com.practica.dto.mapper.MethodPaymentMapper
import com.practica.exception.NumberCardExistException
import com.practica.exception.ValidatedNumberCard
import com.practica.model.MethodPayment
import com.practica.service.MethodPaymentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/type-payment")
class MethodPaymentController constructor(
    val methodPaymentMapper: MethodPaymentMapper,
    val methodPaymentService: MethodPaymentService
) {

    @GetMapping("/list")
    fun getAll(): ResponseEntity<Any> {
        val methodPaymentList: List<MethodPayment> = methodPaymentService.getAll()

        return try {
            if(methodPaymentList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            }
            /**
             * Mapear una lista de entidad a dto
             */
            ResponseEntity.status(HttpStatus.OK).body(methodPaymentList.map { methodPaymentMapper.fromEntityToDto(it) })
        } catch (responseStatusException: ResponseStatusException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf(responseStatusException.message))
        }
    }

    @GetMapping("/get-id")
    fun getById(@RequestParam methodPaymentId: Long): ResponseEntity<Any> {
        val methodPayment: MethodPayment = methodPaymentService.getById(methodPaymentId)!!
        return try {
            if(methodPayment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            }
            ResponseEntity.ok(methodPaymentMapper.fromEntityToDto(methodPayment))
        } catch (responseStatusException: ResponseStatusException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf(responseStatusException.message))
        }
    }

    @GetMapping("/get-userid")
    fun getByUsers(@RequestParam userId: Long): ResponseEntity<Any> {
        val methodPaymentList: List<MethodPayment> = methodPaymentService.getByUsersId(userId)
        return try {
            if(methodPaymentList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            }
            ResponseEntity.status(HttpStatus.OK).body(methodPaymentList.map { methodPaymentMapper.fromEntityToDto(it) })
        } catch (responseStatusException: ResponseStatusException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf(responseStatusException.message))
        }
    }

    @PostMapping("/create")
    fun save(@RequestBody methodPayment: MethodPayment): ResponseEntity<Any> {
        return try {
            if(methodPayment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            }
            ResponseEntity.status(HttpStatus.CREATED).body(methodPaymentMapper.fromEntityToDto(methodPaymentService.save(methodPayment)))
        } catch (validatedNumberCard: ValidatedNumberCard) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(validatedNumberCard)
        } catch (numberCardExistException: NumberCardExistException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(numberCardExistException)
        } catch (responseStatusException: ResponseStatusException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf(responseStatusException.message))
        }
    }

    @PutMapping("/update-user")
    fun updateByUsers(@RequestBody methodPayment: MethodPayment): ResponseEntity<Any> {
        return try {
            if(methodPayment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            }
            ResponseEntity.status(HttpStatus.OK).body(methodPaymentMapper.fromEntityToDto(methodPaymentService.updateByUsers(methodPayment)!!))
        } catch (responseStatusException: ResponseStatusException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf(responseStatusException.message))
        }
    }

    @DeleteMapping("/delete-user")
    fun deleteByUser(@RequestParam methodPaymentId: Long,  @RequestParam userId: Long): ResponseEntity<Any> {
        return try {
            if(methodPaymentId == null || userId == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            }
            ResponseEntity.status(HttpStatus.OK).body(methodPaymentService.deleteByUser(methodPaymentId, userId))
        } catch (responseStatusException: ResponseStatusException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf(responseStatusException.message))
        }
    }
}