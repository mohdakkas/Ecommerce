package com.ecom.Ecommerce.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import com.ecom.Ecommerce.model.UserDtls;

public class CustomUser implements UserDetails {
    
    private UserDtls user;

    public CustomUser(UserDtls user) {
        super();
        this.user = user;
    }
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole()); // ✅ FIX
    return Arrays.asList(authority);
}


    @Override
    public String getPassword() {
        return user.getPassword(); // Ensure password is fetched correctly
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // Use email as username
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
