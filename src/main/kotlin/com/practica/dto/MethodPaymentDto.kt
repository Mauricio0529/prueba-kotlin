package com.practica.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class MethodPaymentDto(

    @JsonProperty("id")
    val id: Long,

    @JsonProperty("usersId")
    val usersId: Long?,

    @JsonProperty("typePayment")
    val typePayment: String?,

    @JsonProperty("numberCard")
    val numberCard: Int?,
) {
}