package de.letsdev.springbootbackendweb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootBackendWebApplication

fun main(args: Array<String>) {
    runApplication<SpringBootBackendWebApplication>(*args)
}
