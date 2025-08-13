package de.propadmin.rentalmanager.dto;

import de.propadmin.rentalmanager.models.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class AppUserDetailsDTO implements UserDetails {
    private final UserAccount userAccount;

    public AppUserDetailsDTO(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Long getId() {
        return userAccount.getId();
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // You can adapt this to return actual roles/authorities if needed
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return userAccount.getPassword();
    }

    @Override
    public String getUsername() {
        return userAccount.getEmail();
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
