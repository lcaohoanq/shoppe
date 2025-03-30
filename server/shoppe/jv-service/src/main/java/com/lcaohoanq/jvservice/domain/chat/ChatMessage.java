package com.lcaohoanq.jvservice.domain.chat;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private Long id;
    private Long chatRoomId;
    private Long senderId;
    private String content;
    private String attachmentUrl;
    private LocalDateTime timestamp;
    private boolean isRead;
    
} 