package com.lcaohoanq.chatservice.domain.chat

import org.springframework.data.jpa.repository.JpaRepository

interface ChatMessageRepository : JpaRepository<ChatMessage, String> {
    fun findByChatId(chatId: String): List<ChatMessage>
}
