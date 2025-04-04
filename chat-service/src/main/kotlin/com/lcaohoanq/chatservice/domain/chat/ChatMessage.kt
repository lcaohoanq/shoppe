package com.lcaohoanq.chatservice.domain.chat

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "chat_message")
class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Long = 0

    @Column(name = "chat_id")
    private var chatId: String = ""

    @Column(name = "sender_id")
    private var senderId: String = ""

    @Column(name = "recipient_id")
    private var recipientId: String = ""

    private var content: String = ""

    private var timestamp: Date = Date()

    fun getId(): Long {
        return id
    }

    fun getSenderId(): String {
        return senderId
    }

    fun setChatId(chatId: String) {
        this.chatId = chatId
    }

    fun getRecipientId(): String {
        return recipientId
    }

    fun getContent(): String {
        return content
    }

    fun getTimestamp(): Date {
        return timestamp
    }
}
