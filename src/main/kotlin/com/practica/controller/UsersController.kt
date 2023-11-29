package com.practica.controller

import com.practica.dto.UsersDto
import com.practica.dto.VehicleDto
import com.practica.dto.mapper.UsersMapper
import com.practica.exception.NumberCardExistException
import com.practica.exception.UserNameAlreadyExistsException
import com.practica.model.Users
import com.practica.service.UsersService
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/users")
class UsersController constructor(
    val usersService: UsersService,
    val mapper: UsersMapper
) {

    @GetMapping("/list")
    fun getAll(): ResponseEntity<Any> {
        val usersList: List<Users> = usersService.getAll()
        return try {
            if(usersList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            }
            ResponseEntity.status(HttpStatus.OK).body(usersList.map { mapper.fromEntityToDto(it) })
        } catch (responseStatusException: ResponseStatusException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf(responseStatusException.message))
        }
    }

    @GetMapping("/get-id")
    fun getById(@RequestParam usersId: Long): ResponseEntity<Any> {
        val users: Users? = usersService.getById(usersId)
        return try {
            if(users == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            }
            return ResponseEntity.status(HttpStatus.OK).body(mapper.fromEntityToDto(users))
        } catch (responseStatusException: ResponseStatusException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf(responseStatusException.message))
        }
    }


    @GetMapping("/get-username")
    fun getByUserName(@RequestParam userName: String): ResponseEntity<Any> {
        val users: Users? = usersService.getByUserName(userName)
        return try {
            if(users == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            }
            return ResponseEntity.status(HttpStatus.OK).body(mapper.fromEntityToDto(users))
        } catch (responseStatusException: ResponseStatusException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf(responseStatusException.message))
        }
    }

    @PostMapping("/create")
    fun save(@RequestBody users: Users): ResponseEntity<Any> {
        return try {
            ResponseEntity.status(HttpStatus.CREATED).body(mapper.fromEntityToDto(usersService.save(users)))

        } catch (userNameAlreadyExistsException: UserNameAlreadyExistsException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(userNameAlreadyExistsException)

        } catch (numberCardExistException: NumberCardExistException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(numberCardExistException)

        } catch (responseStatusException: ResponseStatusException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf(responseStatusException.message))
        }
    }


    @PutMapping("/update")
    fun update(@RequestBody users: Users): ResponseEntity<Any> {
        return try {
            ResponseEntity.status(HttpStatus.CREATED).body(mapper.fromEntityToDto(usersService.update(users)!!))

        } catch (userNameAlreadyExistsException: UserNameAlreadyExistsException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(userNameAlreadyExistsException)

        } catch (numberCardExistException: NumberCardExistException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(numberCardExistException)

        } catch (responseStatusException: ResponseStatusException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf(responseStatusException.message))
        }
    }

    @DeleteMapping("/delete")
    fun delete(@RequestParam userId: Long): ResponseEntity<Any> {
        return try {
            ResponseEntity.status(HttpStatus.OK).body(usersService.delete(userId))
        } catch (ex: EntityNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        } catch (responseStatusException: ResponseStatusException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOf(responseStatusException.message))
        }
    }
}