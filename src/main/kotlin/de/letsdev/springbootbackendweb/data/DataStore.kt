package de.letsdev.springbootbackendweb.data

import de.letsdev.springbootbackendweb.dto.RestaurantDto

object DataStore {
    val restaurants: MutableList<RestaurantDto> = mutableListOf()
}