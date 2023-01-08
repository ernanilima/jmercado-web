package br.com.ernanilima.auth.security;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {
        final String authHeader = request.getHeader(AUTHORIZATION);

        // o cabeçalho existe e começa com o valor 'Bearer '
        if (nonNull(authHeader) && authHeader.startsWith(jwtUtils.getStartToken())) {
            var token = getAuthorization(authHeader.substring(7));
            if (nonNull(token))
                SecurityContextHolder.getContext().setAuthentication(token);
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthorization(String token) {
        if (!jwtUtils.isTokenValid(token)) return null;

        String emailAndParameter = jwtUtils.getUserEmailAndParameter(token);
        UserDetails user = userDetailsService.loadUserByUsername(emailAndParameter);

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}
