package com.practica.service

import com.practica.dto.mapper.RentalMapper
import com.practica.exception.MethodPaymentToUserNotExistException
import com.practica.model.MethodPayment
import com.practica.model.Rental
import com.practica.repository.MethodPaymentRepository
import com.practica.repository.RentalRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class RentalService @Autowired constructor(
    val rentalRepository: RentalRepository,
    val methodPaymentRepository: MethodPaymentRepository,
    val rentalMapper: RentalMapper,
) {
    companion object {
        private const val OPEN: String = "Abierto"
        private const val CLOSED: String = "Cerrado"
    }

    fun getAll(): List<Rental> {
        return rentalRepository.findAll()
    }

    fun getById(rentalId: Long): Rental? {
        val rentalOptional: Optional<Rental> = rentalRepository.findById(rentalId)
        if(rentalOptional.isEmpty) {
            return null
        }
        return rentalOptional.get()
    }

    fun getByUsersId(usersId: Long): Rental? {
        val rentalOptional: Optional<Rental> = rentalRepository.findByUsersId(usersId)
        if(rentalOptional.isEmpty) {
            return null
        }
        return rentalOptional.get()
    }

    fun save(rental: Rental): Rental {
        val newStatus: String = OPEN
        val methodPaymentList: List<MethodPayment> = methodPaymentRepository.findByUsersId(rental.usersId)
            .filter{ it.id == rental.methodPaymentId }.toList()

        if(methodPaymentList.isEmpty()) {
            throw  MethodPaymentToUserNotExistException("El medio de pago no existe en la cuenta de usuario.");
        }
        val rentalSaved: Rental = processSaved(rental, newStatus)
        return rentalRepository.save(rentalSaved)
    }

    /**
     * Establece valores de la relacion
     */
    private fun processSaved(rental: Rental, newStatus: String): Rental {

        rental.dateStart = LocalDateTime.now()
        rental.status = newStatusRental(rental, newStatus).status

        val rentId: Long = rentalRepository.save(rental).id

        val rentalSaved: Rental = rentalRepository.findById(rentId).get();

        rentalSaved.vehiclesList.forEach{vehicleDto -> vehicleDto.rentalId = rentalSaved.id}

        //rentalRepository.save(rentalSaved);
        return rentalSaved
    }

    /**
     * Obtiene un nuevo estado
     */
    private fun newStatusRental(rental: Rental, newStatus: String):Rental {
        rental.status = newStatus
        if(rental.status.equals(CLOSED)) {
            rental.dateFinalized = LocalDateTime.now()
        }
        return rental
    }

    fun updateStatus(rentalId: Long, newStatus: String): Rental? {
        val rental: Rental? = getById(rentalId)
        println("1")
        if(rental == null) {
            println("2")
            return null
        }
        println("3")
        val savedRental: Rental = processSaved(rental, newStatus)
        println("4")
        return rentalRepository.save(savedRental)
    }

}