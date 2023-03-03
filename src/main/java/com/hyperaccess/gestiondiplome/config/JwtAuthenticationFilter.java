package com.hyperaccess.gestiondiplome.config;


import com.hyperaccess.gestiondiplome.exception.EntityNotFoundException;
import com.hyperaccess.gestiondiplome.repository.UtilisateurRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UtilisateurRepository utilisateurRepository;
    private final JwtService jwtService;
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION);
        String userEmail = null;
        String jwt = null;

        if (isNull(authHeader) || !authHeader.startsWith(BEARER)){
            filterChain.doFilter(request,response);
            return;
        }

            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);
            if (nonNull(userEmail) && isNull(SecurityContextHolder.getContext().getAuthentication())){
                UserDetails userDetails = utilisateurRepository.findUtilisateursByEmail(userEmail).orElseThrow(()-> new  EntityNotFoundException("User not found while vadation JWT"));
                if (jwtService.isTokenValid(jwt,userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }

        filterChain.doFilter(request,response);


    }
}
