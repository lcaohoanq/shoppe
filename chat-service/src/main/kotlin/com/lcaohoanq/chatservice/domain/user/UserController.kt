package com.lcaohoanq.chat.domain.user

import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: IUserService
) {


    @MessageMapping("/user.addUser")
    @SendTo("/topic/public")
    fun addUser(
        @Payload user: User
    ): User {
        println("Received user: $user")
        userService.saveUser(user)
        return user
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/topic/public")
    fun disconnectUser(
        @Payload user: User
    ): User {
        userService.disconnect(user)
        return user
    }

    @GetMapping("/users")
    fun findConnectedUsers(): ResponseEntity<List<User>> {
        return ResponseEntity.ok(
            userService.findConnectedUsers()
        )
    }
}
