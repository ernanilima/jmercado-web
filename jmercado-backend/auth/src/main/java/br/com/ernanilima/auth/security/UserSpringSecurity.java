package br.com.ernanilima.auth.security;

import br.com.ernanilima.auth.core.StandardUsers;
import br.com.ernanilima.auth.domain.Role;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
public class UserSpringSecurity implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    private UUID key;
    @Getter
    private String companyEin;
    private String email;
    private String password;
    private Set<Role> authorities;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream().map(x -> new SimpleGrantedAuthority(String.valueOf(x.getId())))
                .collect(Collectors.toList());
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

    /**
     * Obter os dados do usuario logado
     *
     * @return UserSpringSecurity
     */
    public static UserSpringSecurity getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return UserSpringSecurity.builder()
                    .key(StandardUsers.UNAUTHENTICATED_UUID)
                    .build();
        }

        return (UserSpringSecurity) authentication.getPrincipal();
    }
}
