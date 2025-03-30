package com.lcaohoanq.jvservice.util

import java.awt.Desktop
import java.net.URI
import java.util.*

fun openHomePage(url: String) {
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