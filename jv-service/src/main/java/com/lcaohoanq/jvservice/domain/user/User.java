package com.lcaohoanq.jvservice.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lcaohoanq.jvservice.base.entity.BaseEntity;
import com.lcaohoanq.jvservice.domain.cart.Cart;
import com.lcaohoanq.jvservice.domain.chat.ChatRoom;
import com.lcaohoanq.jvservice.domain.role.Role;
import com.lcaohoanq.jvservice.domain.wallet.Wallet;
import com.lcaohoanq.common.enums.UserStatus;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@Getter
@Setter
@SuperBuilder
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
    private String password;

    private String name;

    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @Column(name="is_active", columnDefinition = "boolean default true")
    @JsonProperty("is_active")
    private boolean isActive;

    @Enumerated(EnumType.ORDINAL)
    private UserStatus status;

    @Column(name="date_of_birth")
    private String dateOfBirth;

    private String avatar;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Address> address;

    @Unique
    @Column(name="phone_number",nullable = false, length = 100)
    @JsonProperty("phone_number")
    private String phoneNumber;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Cart cart;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference //to prevent infinite loop
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

    @OneToMany(mappedBy = "user1")
    @JsonIgnore
    private List<ChatRoom> initiatedChats = new ArrayList<>();

    @OneToMany(mappedBy = "user2")
    @JsonIgnore
    private List<ChatRoom> receivedChats = new ArrayList<>();

    @Getter
    @AllArgsConstructor
    public enum Gender {

        MALE("MALE"),
        FEMALE("FEMALE"),
        OTHER("OTHER");

        private final String value;

        @JsonValue
        public int toValue() {
            return ordinal();
        }

    }
    
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
