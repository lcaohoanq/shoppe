package com.lcaohoanq.jvservice.domain.socialaccount;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.common.enums.ProviderName;
import com.lcaohoanq.jvservice.domain.user.User;
import com.lcaohoanq.jvservice.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "social_accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SocialAccount extends BaseEntity {

    @Id
    @SequenceGenerator(name = "social_accounts_seq", sequenceName = "social_accounts_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "social_accounts_seq")
    @Column(name="id", unique=true, nullable=false)
    @JsonProperty("id")
    private Long id;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "provider_name")
    private ProviderName providerName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}