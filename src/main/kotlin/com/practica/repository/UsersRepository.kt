package com.practica.repository

import com.practica.model.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UsersRepository: JpaRepository<Users, Long> {
    fun findByUserName(username: String): Optional<Users>
}