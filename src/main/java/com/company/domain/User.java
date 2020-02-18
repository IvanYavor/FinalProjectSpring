package com.company.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Size(min = 2, max = 20, message = "Invalid Username")
    private String username;

    @NotNull
    @Pattern(regexp = "^[A-Z][a-z]*(\\s[A-Z][a-z]*)+$", message = "Invalid Full Name")
    private String fullName;


    @ManyToOne(cascade = {CascadeType.ALL})
    @CollectionTable(name = "speciality_class_in_user", joinColumns = @JoinColumn(name = "user_id", nullable = false))
    private SpecialityClass specialityClass = new SpecialityClass();

    @NotNull
    @Size(min = 1, max = 36, message = "Invalid Password")
    private String password;

    @Column(name = "is_accepted", columnDefinition = "boolean default false")
    private boolean isAccepted;

    @Column(columnDefinition = "boolean default true")
    private boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;


    public User() {
    }

    public User(String username, String fullName) {
        this.username = username;
        this.fullName = fullName;
    }


    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
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

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public SpecialityClass getSpecialityClass() {
        return specialityClass;
    }

    public void setSpecialityClass(SpecialityClass specialityClass) {
        this.specialityClass = specialityClass;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
