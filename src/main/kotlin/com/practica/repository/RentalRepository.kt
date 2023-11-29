package com.practica.repository

import com.practica.model.Rental
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface RentalRepository: JpaRepository<Rental, Long> {
    fun findByUsersId(userId: Long): Optional<Rental>
}