package com.lcaohoanq.shoppe.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.constants.Regex;
import com.lcaohoanq.shoppe.enums.Country;
import com.lcaohoanq.shoppe.enums.Currency;
import com.lcaohoanq.shoppe.enums.Gender;
import com.lcaohoanq.shoppe.enums.UserStatus;
import com.lcaohoanq.shoppe.models.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {

    @Id
    @SequenceGenerator(name = "users_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @Column(name="id", unique=true, nullable=false)
    @JsonProperty("id")
    private Long id;

    @Unique
    @Email
    @Column(name="email",nullable = false, length = 100)
    private String email;

    @Column(name="password", length = 200)
    @JsonProperty("password")
    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name="is_active", columnDefinition = "boolean default true")
    @JsonProperty("is_active")
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    @JsonProperty("status")
    private UserStatus status;

    @Column(name="date_of_birth")
    private String dateOfBirth;

    private String avatar;

    private String address;

    @Unique
    @Column(name="phone_number",nullable = false, length = 100)
    @JsonProperty("phone_number")
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;
    
    @Column(name = "preferred_language")
    private String preferredLanguage;
    
    @Column(name = "preferred_currency")
    private String preferredCurrency;

    @Column(name = "last_login_timestamp")
    private LocalDateTime lastLoginTimestamp;

    //Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_"+getRole().getUserRole().name()));
        //authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return authorityList;
    }

    //why getUserName() is return email
    //because in the UserDetailsService, we use email to find user
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
