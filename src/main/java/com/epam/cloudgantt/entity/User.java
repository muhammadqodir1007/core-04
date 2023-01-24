package com.epam.cloudgantt.entity;

import com.epam.cloudgantt.entity.template.AbsUUIDEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "users")
@Getter
@Setter
@Accessors(chain = true)
public class User extends AbsUUIDEntity implements UserDetails {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String verificationCode;

    private boolean enabled;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    @OneToOne(mappedBy = "user")
    private Project project;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
