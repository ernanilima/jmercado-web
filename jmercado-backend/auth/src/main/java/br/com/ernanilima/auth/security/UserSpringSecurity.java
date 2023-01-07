package br.com.ernanilima.auth.security;

import br.com.ernanilima.auth.domain.Role;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
public class UserSpringSecurity implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    private String companyEin;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSpringSecurity(String companyEin, String email, String password, Set<Role> authorities) {
        this.companyEin = companyEin;
        this.email = email;
        this.password = password;
        this.authorities = authorities.stream().map(x -> new SimpleGrantedAuthority(String.valueOf(x.getId())))
                .collect(Collectors.toList());
    }

    public String getCompanyEin() {
        return companyEin;
    }

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
        return authorities;
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
        return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
