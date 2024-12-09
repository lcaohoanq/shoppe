package com.lcaohoanq.shoppe.domain.wallet;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.domain.user.User;
import com.lcaohoanq.shoppe.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "wallets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wallet extends BaseEntity {

    @Id
    @SequenceGenerator(name = "wallets_seq", sequenceName = "wallets_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallets_seq")
    @Column(name="id", unique=true, nullable=false)
    @JsonProperty("id")
    private Long id;

    @Column(name = "balance", nullable = false)
    private Float balance;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference // Prevent infinite loop when serializing
    private User user;

}
