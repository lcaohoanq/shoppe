package com.lcaohoanq.chatservice.domain.chat

import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController(
    private val messagingTemplate: SimpMessagingTemplate,
    private val chatMessageService: IChatMessageService
) {

    @MessageMapping("/chat")
    fun processMessage(@Payload chatMessage: ChatMessage) {
        val savedMsg = chatMessageService.save(chatMessage)
        messagingTemplate.convertAndSendToUser(
            chatMessage.getRecipientId(), "/queue/messages",
            ChatNotification(
                savedMsg.getId(),
                savedMsg.getSenderId(),
                savedMsg.getRecipientId(),
                savedMsg.getContent()
            )
        )
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    fun findChatMessages(
        @PathVariable senderId: String,
        @PathVariable recipientId: String
    ): ResponseEntity<List<ChatMessage>> = ResponseEntity.ok(
        chatMessageService.findChatMessages(senderId, recipientId)
    )

}
