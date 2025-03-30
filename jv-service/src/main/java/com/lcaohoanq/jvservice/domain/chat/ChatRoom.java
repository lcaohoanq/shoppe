package com.lcaohoanq.jvservice.domain.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.jvservice.base.entity.BaseEntity;
import com.lcaohoanq.jvservice.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "chat_rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ChatRoom extends BaseEntity {

    @Id
    @SequenceGenerator(name = "chat_rooms_seq", sequenceName = "chat_rooms_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_rooms_seq")
    @Column(name = "id", unique = true, nullable = false)
    @JsonProperty("id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_1_id", nullable = false)
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user_2_id", nullable = false)
    private User user2;

    @Column(name = "user1_typing_status")
    private Boolean user1TypingStatus = false;

    @Column(name = "user2_typing_status")
    private Boolean user2TypingStatus = false;

}