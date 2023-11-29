package com.practica.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class RentalDto(

    @JsonProperty("id")
    val id: Long,

    @JsonProperty("usersId")
    val usersId: Long?,

    @JsonProperty("methodPaymentId")
    val methodPaymentId: Long?,

    @JsonProperty("valueRental")
    val valueRental: Double?,

    @JsonProperty("dateStart")
    val dateStart: LocalDateTime?,

    @JsonProperty("dateFinalized")
    val dateFinalized: LocalDateTime?,

    @JsonProperty("status")
    val status: String?,

    @JsonProperty("vehiclesList")
    val vehiclesList: List<VehicleDto>
) {
}