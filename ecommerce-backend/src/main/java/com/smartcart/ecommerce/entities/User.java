package com.smartcart.ecommerce.entities;

import com.smartcart.ecommerce.enums.Provider;
import com.smartcart.ecommerce.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "users")
public class User extends AuditEntity implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = true)
    private String password;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(nullable = false)
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    @Column(nullable = false)
    @CollectionTable(
            name = "user_roles",
            joinColumns = {
                    @JoinColumn(name = "user_id")
            }
    )
    private Set<Roles> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities=new HashSet<>();
        this.roles
                .stream()
                .forEach(r->{
                    authorities.add(new SimpleGrantedAuthority("ROLE_"+r.name()));
                    r.getPermissions()
                            .forEach(p->authorities.add(new SimpleGrantedAuthority(p.name())));
                });

        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }


}
