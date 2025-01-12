package com.lcaohoanq.shoppe

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
@EnableJpaRepositories(basePackages = ["com.lcaohoanq.shoppe.domain"])
open class Shoppe

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"))
    SpringApplication.run(Shoppe::class.java, *args)
    openHomePage("http://localhost:8080/swagger-ui/index.html")
}

private fun openHomePage(url: String) {
    try {
        if (Desktop.isDesktopSupported()) {
            val desktop = Desktop.getDesktop()
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(URI.create(url))
            }
        } else {
            val os = System.getProperty("os.name").lowercase(Locale.getDefault())
            if (os.contains("win")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler $url")
            } else if (os.contains("mac")) {
                Runtime.getRuntime().exec("open $url")
            } else if (os.contains("nix") || os.contains("nux")) {
                Runtime.getRuntime().exec("xdg-open $url")
            } else {
                println("Unsupported operating system: $os")
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}