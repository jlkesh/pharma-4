package com.onlinepharma.onlinepharma.domain.auth;


import com.onlinepharma.onlinepharma.domain.Auditable;
import com.onlinepharma.onlinepharma.enums.Language;
import com.onlinepharma.onlinepharma.enums.Status;
import com.onlinepharma.onlinepharma.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "auth_users")
@Entity
public class Users extends Auditable {

    @Column(nullable = false, unique = true)
    private String principal;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private UUID code = UUID.randomUUID();

    @Enumerated(EnumType.STRING)
    private Language language;

    @Enumerated(EnumType.STRING)
    private Status status;


    @Enumerated(EnumType.STRING)
    @Column(length = 32, columnDefinition = "varchar(32) default 'PATIENT'")
    private UserType userType;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "auth_user_roles",
            joinColumns = @JoinColumn(name = "auth_user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "auth_role_id", referencedColumnName = "id")
    )
    private Collection<Role> roles;

}
