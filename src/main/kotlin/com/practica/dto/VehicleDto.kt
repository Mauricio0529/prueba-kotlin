package com.practica.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class VehicleDto(
    @JsonProperty("id")
    val id: Long,

    @JsonProperty("rental_id")
    val rentalId: Long?,

    @JsonProperty("name_vehicle")
    val nameVehicle: String?,

    @JsonProperty("model_year")
    val modelYear: String?,

    ) {
}