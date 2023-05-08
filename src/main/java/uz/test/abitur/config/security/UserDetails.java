package uz.test.abitur.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import uz.test.abitur.domains.AuthUser;
import uz.test.abitur.enums.Status;

import java.util.Collection;
import java.util.Collections;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final AuthUser authUser;

    public UserDetails(AuthUser authUser) {
        this.authUser = authUser;
    }

    public AuthUser getAuthUser() {
        return authUser;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authUser.getRole() == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + authUser.getRole()));
    }

    @Override
    public String getPassword() {
        return authUser.getPassword();
    }

    @Override
    public String getUsername() {
        return authUser.getPhoneNumber();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !Status.BLOCKED.equals(authUser.getStatus());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Status.ACTIVE.equals(authUser.getStatus());
    }
}
