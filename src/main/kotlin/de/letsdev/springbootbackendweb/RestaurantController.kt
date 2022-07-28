package de.letsdev.springbootbackendweb

import de.letsdev.springbootbackendweb.config.EndpointConstants
import de.letsdev.springbootbackendweb.data.DataStore
import de.letsdev.springbootbackendweb.dto.RestaurantDto
import de.letsdev.springbootbackendweb.services.api.RestaurantService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
@RequestMapping(EndpointConstants.RESTAURANTS)
class RestaurantController(@Autowired private val restaurantService: RestaurantService) {

    @GetMapping(EndpointConstants.RESTAURANTS_ALL)
    fun getAllData(): ResponseEntity<MutableList<RestaurantDto>> {
        val req = restaurantService.findAllRestaurants()
        println(">>> Looking up all restaurants." + "$req")
        return ResponseEntity(req, HttpStatus.OK)
    }

    @GetMapping(EndpointConstants.RESTAURANTS_SPECIFIC)
    fun getSpecificData(@RequestParam name: String?, location: String?, customers: Int?, page: Int, limit: Int): List<RestaurantDto> {
        val restau = restaurantService.findSpecificRestaurant(name, location, customers, page, limit)
        return restau
    }


    @PostMapping(EndpointConstants.RESTAURANTS_CREATE)
    fun setData(@RequestParam name: String, location: String, customers: Int): ResponseEntity<RestaurantDto> {
//        return ResponseEntity()
        val findId = DataStore.restaurants.maxOfOrNull { it.id } ?: 0
        restaurantService.addRestaurant(name, location, customers, findId + 1)
        return ResponseEntity(RestaurantDto(name, location, customers, findId + 1), HttpStatus.OK)
    }

//    @PutMapping

    @DeleteMapping(EndpointConstants.RESTAURANTS_DELETE)
    fun deleteData(@RequestParam name: String, location: String): HttpStatus {
        return try {
            restaurantService.deleteRestaurant(name, location)
            HttpStatus.OK
        } catch (err: Throwable) {
            HttpStatus.NOT_FOUND
        }

    }

    @PutMapping(EndpointConstants.RESTAURANTS_MODIFY)
    fun modifyData(@RequestParam id: Long, newName: String?, newLocation: String?, newCustomerCount: Int?): String {
        return try {
            restaurantService.modifyRestaurantEntry(id, newName, newLocation, newCustomerCount)
            "Changed restaurant with the id: $id to -> Name: $newName at Location: $newLocation"
        } catch (err: Throwable) {
            "Ups, something went wrong. :( ==> $err"
        }
    }
}