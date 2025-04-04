package com.lcaohoanq.chatservice.domain.chatroom

import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomRepository : JpaRepository<ChatRoom, String> {
    fun findBySenderIdAndRecipientId(senderId: String, recipientId: String): ChatRoom?
}
