package de.letsdev.springbootbackendweb.services.api

import de.letsdev.springbootbackendweb.dto.RestaurantDto
import org.springframework.http.HttpStatus
import java.net.http.HttpResponse

interface RestaurantService {
    fun addRestaurant(name: String, location: String, customers: Int, id: Long)
    fun findAllRestaurants(): MutableList<RestaurantDto>

    fun findSpecificRestaurant(name: String?, location: String?, customers: Int?, page: Int, limit: Int): List<RestaurantDto>

    fun deleteRestaurant(name: String, location: String): HttpStatus

    fun modifyRestaurantEntry(id: Long, newName: String?, newLocation: String?, newCustomerCount: Int?): String
}