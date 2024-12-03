package com.lcaohoanq.shoppe.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.enums.UserRole;
import com.lcaohoanq.shoppe.models.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
@Entity
@Builder
public class Role extends BaseEntity {

    @Id
    @SequenceGenerator(name = "roles_seq", sequenceName = "roles_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq")
    @Column(name="id", unique=true, nullable=false)
    @JsonProperty("id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private UserRole userRole;

//    @OneToMany(mappedBy = "role")
//    private List<User> users;

    public Role(UserRole userRole) {
        this.userRole = userRole;
    }

    public static String MEMBER = "MEMBER";
    public static String STAFF = "STAFF";
    public static String STORE_OWNER = "STORE_OWNER";
    public static String MANAGER = "MANAGER";
}
