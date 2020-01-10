package com.company.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String fullName;
//
//    @ManyToOne
//    @JoinColumn(name="user_id")
//    private SpecialityClass specialityClass;

    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name="user_scores2")
    @MapKeyColumn(name="classLesson")
    @Column(name="score")
    private Map<String, Integer> scores = new HashMap<String, Integer>();

    private String email;
    private String password;
    private boolean active;

    @ElementCollection(targetClass =  Role.class, fetch = FetchType.EAGER)
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
        return isActive();
    }


    public Map<String, Integer> getScores() {
        return scores;
    }

//    public SpecialityClass getSpecialityClass() {
//        return specialityClass;
//    }
//
//    public void setSpecialityClass(SpecialityClass specialityClass) {
//        this.specialityClass = specialityClass;
//    }

    public void setScores(Map<String, Integer> scores) {
        this.scores = scores;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
