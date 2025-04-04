package com.lcaohoanq.chatservice.domain.chatroom

import jakarta.persistence.*

@Entity
@Table(name = "chat_room")
class ChatRoom(
    @Column(name = "chat_id")
    var chatId: String,

    @Column(name = "sender_id")
    var senderId: String,

    @Column(name = "recipient_id")
    var recipientId: String
) {
    constructor() : this("", "", "")

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0
}

