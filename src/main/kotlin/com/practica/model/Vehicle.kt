package com.practica.model

import jakarta.persistence.*

@Entity
@Table(name = Vehicle.TABLE_NAME)
data class Vehicle(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN_NAME)
    val id: Long = 0,

    @Column(name = RENTAL_ID_COLUMN_NAME)
    var rentalId: Long? = null,

    @Column(name = NAME_VEHICLE_COLUMN_NAME)
    val nameVehicle: String? = null,

    @Column(name = MODEL_YEAR_COLUMN_NAME)
    val modelYear: String? = null,

    @ManyToOne()
    @JoinColumn(name = "rentalId", insertable = false, updatable = false)
    val rental: Rental? = null
) {
    companion object {
        const val TABLE_NAME = "VEHICLE"
        const val ID_COLUMN_NAME = "ID_COLUMN_NAME"
        const val RENTAL_ID_COLUMN_NAME = "rentalId"
        const val NAME_VEHICLE_COLUMN_NAME = "NAME_VEHICLE"
        const val MODEL_YEAR_COLUMN_NAME = "MODEL_YEAR"
        const val PRICE_VEHICLE_RENTAL_COLUMN_NAME = "PRICE_VEHICLE_RENTAL"
    }
}