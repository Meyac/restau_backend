package de.letsdev.springbootbackendweb.db

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.Id
import java.math.BigDecimal
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "RESTAURANT")
open class RestaurantEntity {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RESTAURANT_ID", nullable = false)
    open var id: Long? = null

    @Column(name = "RESTAURANT_NAME", length = 100)
    open var name: String? = null

    @Column(name = "RESTAURANT_LOCATION", nullable = false, length = 100)
    open var location: String? = null

    @Column(name = "RESTAURANT_CUSTOMERCOUNT", nullable=false)
    open var customerCount: Number? = null

}