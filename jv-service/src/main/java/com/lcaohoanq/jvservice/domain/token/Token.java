package com.lcaohoanq.jvservice.domain.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.jvservice.domain.user.User;
import com.lcaohoanq.jvservice.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tokens")
public class Token extends BaseEntity {

    @Id
    @SequenceGenerator(name = "tokens_seq", sequenceName = "tokens_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tokens_seq")
    @Column(name="id", unique=true, nullable=false)
    @JsonProperty("id")
    private Long id;

    @Column(name = "token", length = 255)
    private String token;

    @Column(name = "refresh_token", length = 255)
    private String refreshToken;

    @Column(name = "token_type", length = 50)
    private String tokenType;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "refresh_expiration_date")
    private LocalDateTime refreshExpirationDate;

    @Column(name = "is_mobile")
    private boolean isMobile;

    private boolean revoked;
    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}