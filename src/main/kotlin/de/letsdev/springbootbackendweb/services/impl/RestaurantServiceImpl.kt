package de.letsdev.springbootbackendweb.services.impl

import de.letsdev.springbootbackendweb.data.DataStore
import de.letsdev.springbootbackendweb.dto.RestaurantDto
import de.letsdev.springbootbackendweb.services.api.RestaurantService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.net.http.HttpResponse
import javax.xml.crypto.Data

@Service
class RestaurantServiceImpl: RestaurantService {
    val dataRef = DataStore.restaurants
    override fun addRestaurant(name: String, location: String, customers: Int, id: Long) {
        val findId = DataStore.restaurants.maxOfOrNull { it.id } ?: 0
        DataStore.restaurants.add(RestaurantDto(name, location, customers, id))
    }

    override fun findAllRestaurants(): MutableList<RestaurantDto> {
        return DataStore.restaurants
    }

    override fun findSpecificRestaurant(name: String?, location: String?, customers: Int?, page: Int, limit: Int): List<RestaurantDto> {
        var searchResults = listOf<RestaurantDto>()
        println("======> customers: $customers")
        if(name !== null && location !== null || name !== null && location === null) {
            searchResults = dataRef.filter {
                it.name.lowercase() == name.lowercase()
            }

            println("name != null")
            if(location !== null) {
                searchResults = searchResults.filter{
                    it.location.lowercase() == location.lowercase()
                }
            }
        } else if(customers != null) {
            searchResults = dataRef.filter {
                it.customers >= customers
            }
        } else {
            searchResults = dataRef.filter {
                it.location.lowercase() == location?.lowercase() ?: location
            }
            println("_name is null $searchResults -> $name")

            if(name !== null) {
                searchResults = searchResults.filter{
                    it.name.lowercase() == name.lowercase()
                }
            }
        }

        val searchLimit = (limit-1) * (page+1)
        return if(searchLimit > searchResults.size) searchResults.slice((page*limit) ..(searchResults.size-1)) else searchResults.slice((page*limit)..searchLimit)
    }

    override fun deleteRestaurant(name: String, location: String): HttpStatus {
        val toDelete = dataRef.find {
            it.name == name && it.location == location
        }
        println("Index of item to delete --> $toDelete")
        DataStore.restaurants.remove(toDelete)

        return HttpStatus.OK
    }

    override fun modifyRestaurantEntry(
        id: Long,
        newName: String?,
        newLocation: String?,
        newCustomerCount: Int?
    ): String {
        val changedRestau = dataRef.find{ it.id == id };
        val idx = DataStore.restaurants.indexOf(changedRestau);
        val newRestauEntry: RestaurantDto

        if (changedRestau !== null) {
            newRestauEntry = RestaurantDto(
                if (newName !== null) newName else changedRestau.name,
                if (newLocation !== null) newLocation else changedRestau.location,
                if (newCustomerCount !== null) newCustomerCount else changedRestau.customers,
                id
            )

            DataStore.restaurants[idx] = newRestauEntry
            return "Changed restaurant successfully"
        }

        return "Couldn't find mentioned restaurant."

    }
}