package com.practica.service

import com.practica.exception.NumberCardExistException
import com.practica.exception.UserNameAlreadyExistsException
import com.practica.model.Users
import com.practica.repository.MethodPaymentRepository
import com.practica.repository.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class UsersService @Autowired constructor(
    val usersRepository: UsersRepository,
    val methodPaymentRepository: MethodPaymentRepository){

    fun getAll(): List<Users> {
        return usersRepository.findAll()
    }

    fun getById(usersId: Long): Users? {
        val usersOptional: Optional<Users> = usersRepository.findById(usersId)
        if(usersOptional.isEmpty) {
            return null
        }
        return usersOptional.get()
    }

    fun getByUserName(userName: String): Users? {
        val usersOptional: Optional<Users> = usersRepository.findByUserName(userName)
        if(usersOptional.isEmpty) {
            return null
        }
        return usersOptional.get()
    }

    fun save(users: Users): Users {
        val savedUsers: Users = processSaved(users)
        return usersRepository.save(savedUsers)
    }

    fun update(users: Users): Users? {
        if(getById(users.id) == null) {
            return null
        }
        val savedUsers: Users = processSaved(users)

        return usersRepository.save(savedUsers)
    }

    /**
     * VALIDA Y GUARDA UN USUARIO
     */
    private fun processSaved(users: Users): Users {
        if (getByUserName(users.userName.toString()) != null) {
            throw UserNameAlreadyExistsException("El nombre de usuario ingresado ya existe");
        }

        users.methodPaymentList.stream().forEach{
            val numberCardRepeated: Int = methodPaymentRepository.countByNumberCard(it.numberCard!!)
            if(numberCardRepeated != 0) {
                throw NumberCardExistException("El numero de tarjeta ya existe")
            }
        }
        users.dateRegistration = LocalDateTime.now()

        val usersId: Long = usersRepository.save(users).id

        val savedUser: Users = usersRepository.findById(usersId).get()

        savedUser.methodPaymentList.stream().forEach{ it.usersId = usersId }
//        return usersRepository.save(savedUser)
        return savedUser
    }

    fun delete(usersId: Long): Boolean {
        if(getById(usersId) == null) {
            return false
        }
        usersRepository.deleteById(usersId)
        return true
    }
}