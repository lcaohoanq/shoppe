package com.lcaohoanq.kotlinbasics.utils

import java.awt.Desktop
import java.net.URI
import java.util.*

var urlList = listOf(
    "http://localhost:8080/swagger-ui/index.html",
//    "http://localhost:8080/h2-console",
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