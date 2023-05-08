package uz.test.abitur.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.test.abitur.domains.AuthUser;
import uz.test.abitur.repositories.AuthUserRepository;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SessionUser {

    public AuthUser user() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (Objects.isNull(authentication) || authentication instanceof AnonymousAuthenticationToken)
            return null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDT)
            return userDT.getAuthUser();
        return null;
    }

    public String id() {
        AuthUser user = user();
        if (Objects.isNull(user))
            return "-1";
        return user.getId();
    }
}
