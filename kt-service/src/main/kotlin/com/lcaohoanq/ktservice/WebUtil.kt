package com.lcaohoanq.ktservice

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.awt.Desktop
import java.net.URI
import java.util.*


object WebUtil {
    val currentRequest: HttpServletRequest
        get() = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request

    var urlList = listOf(
        "http://localhost:4000/swagger-ui/index.html",
//    "http://localhost:4000/h2-console",
    )

    fun openHomePage() {
        try {
            urlList.forEach { url ->
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
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
