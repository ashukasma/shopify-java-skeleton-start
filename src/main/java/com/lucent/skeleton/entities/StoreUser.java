package com.lucent.skeleton.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author lucent
 */
@Data
@Entity
@Table(name = "store_users")
public class StoreUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "store url is required")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "password is required")
    @Column(name = "password")
    private String password;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Date create_At;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date update_At;

    public static StoreUser build( String username, String password) {
        StoreUser storeUser = new StoreUser();
        storeUser.setUsername(username);
        storeUser.setPassword(password);
        return storeUser;
    }


    @PrePersist
    protected void onCreate() {
        this.create_At = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.update_At = new Date();
    }


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }


}
