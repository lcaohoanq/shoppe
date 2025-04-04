package com.lcaohoanq.chatservice.domain.chat

interface IChatMessageService {
    fun save(chatMessage: ChatMessage): ChatMessage
    fun findChatMessages(senderId: String, recipientId: String): List<ChatMessage>
}
