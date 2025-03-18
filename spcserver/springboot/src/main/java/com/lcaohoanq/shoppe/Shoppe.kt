package com.lcaohoanq.shoppe

import com.lcaohoanq.shoppe.util.openHomePage
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.retry.annotation.EnableRetry
import java.awt.Desktop
import java.net.URI
import java.util.*

@SpringBootApplication
@EnableCaching
@EnableRetry
@EnableJpaRepositories(basePackages = ["com.lcaohoanq.shoppe.repository"])
class Shoppe

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"))
    SpringApplication.run(Shoppe::class.java, *args)
    openHomePage("http://localhost:8080/graphiql")
    openHomePage("http://localhost:8080/swagger-ui/index.html")
}

