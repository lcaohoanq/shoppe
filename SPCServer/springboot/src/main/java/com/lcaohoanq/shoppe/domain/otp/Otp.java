package com.lcaohoanq.shoppe.domain.otp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "otps")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Otp extends BaseEntity {

    @Id
    @SequenceGenerator(name = "otps_seq", sequenceName = "otps_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "otps_seq")
    @Column(name="id", unique=true, nullable=false)
    @JsonProperty("id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "otp")
    private String otp;

    @JsonProperty("expired_at")
    @Column(name = "expired_at")
    private LocalDateTime expiredAt; // in milliseconds

    @JsonProperty("is_used")
    @Column(name = "is_used")
    private boolean isUsed;

    @JsonProperty("is_expired")
    @Column(name = "is_expired")
    private boolean isExpired;

}
