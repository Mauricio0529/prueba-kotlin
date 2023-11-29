package com.practica.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class UsersDto(

    @JsonProperty("id")
    val id: Long,

    @JsonProperty("userName")
    val userName: String? = null,

    @JsonProperty("name")
    val name: String? = null,

    @JsonProperty("lasName")
    val lasName: String? = null,

    @JsonProperty("password")
    val password: String? = null,

    @JsonProperty("dateRegistration")
    val dateRegistration: LocalDateTime? = null,

    val methodPaymentList: List<MethodPaymentDto>

    ) {
}