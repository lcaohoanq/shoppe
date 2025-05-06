package com.lcaohoanq.gateway.controller

import com.lcaohoanq.gateway.qr.QrLoginSession
import com.lcaohoanq.gateway.qr.QrStatus
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@RestController
@Tag(name = "QrLogin", description = "QR Login API")
@RequestMapping("/qr-login")
@CrossOrigin(origins = ["*"]) // Allow cross-origin requests for development
class QrLoginController {

    val qrSessions = ConcurrentHashMap<String, QrLoginSession>()

    // Periodic cleanup of expired sessions
    @Scheduled(fixedRate = 60000) // Run every minute
    fun cleanupExpiredSessions() {
        val expiredSessions = qrSessions.entries.filter { it.value.isExpired() }
        expiredSessions.forEach { (id, session) ->
            session.status = QrStatus.EXPIRED
            // Optional: remove expired sessions after some time
            qrSessions.remove(id)
        }
    }

    // 1. Web requests new session
    @PostMapping
    @Operation(
        summary = "Create a new QR login session",
        description = "This endpoint creates a new QR login session and returns the session ID."
    )
    @CrossOrigin(origins = ["http://localhost:5173"]) // Allow cross-origin requests for development
    fun createSession(): ResponseEntity<String> {
        val sessionId = UUID.randomUUID().toString()
        qrSessions[sessionId] = QrLoginSession(sessionId)
        return ResponseEntity.ok(sessionId)
    }

    // 2. Web polls session
    @GetMapping("/{sessionId}")
    @Operation(
        summary = "Get session status",
        description = "This endpoint retrieves the status of a QR login session by its ID."
    )
    @CrossOrigin(origins = ["http://localhost:5173"]) // Allow cross-origin requests for development
    fun getSessionStatus(@PathVariable sessionId: String): ResponseEntity<QrLoginSession> {
        val session = qrSessions[sessionId] ?: return ResponseEntity.notFound().build()

        // Check if session has expired
        if (session.isExpired() && session.status == QrStatus.PENDING) {
            session.status = QrStatus.EXPIRED
        }

        return ResponseEntity.ok(session)
    }

    // 3. Mobile approves session
    @Operation(
        summary = "Approve QR login session",
        description = "This endpoint allows a mobile user to approve a QR login session."
    )
    @PostMapping("/{sessionId}/approve")
    fun approveSession(
        @PathVariable sessionId: String,
        principal: Principal? = null, // Mobile user's auth (optional for demo)
        @RequestHeader("Authorization") authorization: String? = null // Alternative auth method
    ): ResponseEntity<Any> {
        val session = qrSessions[sessionId] ?: return ResponseEntity.notFound().build()

        if (session.status != QrStatus.PENDING) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(mapOf("error" to "Session is not pending"))
        }

        if (session.isExpired()) {
            session.status = QrStatus.EXPIRED
            return ResponseEntity.status(HttpStatus.GONE)
                .body(mapOf("error" to "Session has expired"))
        }

        // Extract user information from Principal or Authorization header
        val userId = when {
            principal != null -> principal.name
            authorization != null -> {
                try {
                    // Extract user ID from Bearer token (simplified for demo)
                    val token = authorization.replace("Bearer ", "")
                    // In a real app, you would validate the token and extract the user ID
                    // For this demo, we'll just use the token as the user ID
                    token
                } catch (e: Exception) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(mapOf("error" to "Invalid authorization"))
                }
            }

            else -> "anonymous-user" // For demo purposes only
        }

        // Update session status
        session.status = QrStatus.APPROVED
        session.userId = userId

        return ResponseEntity.ok(mapOf("message" to "Login approved"))
    }
}
