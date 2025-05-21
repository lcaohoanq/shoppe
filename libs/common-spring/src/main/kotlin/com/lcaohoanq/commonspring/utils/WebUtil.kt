package com.lcaohoanq.commonspring.utils

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.awt.Desktop
import java.net.URI
import java.util.*


object WebUtil {
    val currentRequest: HttpServletRequest
        get() = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request

    val urlListKotLin = listOf(
        "http://localhost:4000/swagger-ui/index.html",
//    "http://localhost:4000/h2-console",
    )

    val urlListJava = listOf(
        "http://localhost:8080/swagger-ui/index.html",
        "http://localhost:8080/graphiql"
    )

    val urlListGateWay = listOf(
        "http://localhost:4003/kt/swagger", //kt-service
        "http://localhost:4003/jv/swagger", //jv-service
    )

    fun openHomePage(urls: Any) {
        try {
            val urlList = when (urls) {
                is List<*> -> urls.filterIsInstance<String>() // Handles if it's a List<String>
                is String -> listOf(urls) // Converts a single URL to a list
                else -> throw IllegalArgumentException("Invalid argument type")
            }

            val desktop = if (Desktop.isDesktopSupported()) Desktop.getDesktop() else null
            val os = System.getProperty("os.name").lowercase(Locale.getDefault())

            for (url in urlList) {
                if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(URI.create(url))
                } else {
                    when {
                        os.contains("win") -> Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler $url")
                        os.contains("mac") -> Runtime.getRuntime().exec("open $url")
                        os.contains("nix") || os.contains("nux") -> Runtime.getRuntime().exec("xdg-open $url")
                        else -> println("Unsupported operating system: $os")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
