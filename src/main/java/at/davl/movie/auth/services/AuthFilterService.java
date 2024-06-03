package at.davl.movie.auth.services;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class AuthFilterService extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public AuthFilterService(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
                                    // import org.springframework.lang.NonNull;
                                    @NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // get bearer token from request header
        final String authHeader = request.getHeader("Authorization");
        String jwt;
        String username;

        // if header == null or it isn't start with "Bearer
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // if there is token inside header then:
        // extract token -> "Bearer " is 7 characters
        jwt = authHeader.substring(7);

        // extract username from jwt
        username = jwtService.extractUsername(jwt);

        // check if username exists and if user is not authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // load from DB user info and put it in the userDetails
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // check if token is valid
            if(jwtService.isTokenValid(jwt, userDetails)){
                // starting create new token
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authenticationToken.setDetails(
                        // builds the details object from an HttpServletRequest object,
                        // creating a WebAuthenticationDetails
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }


}
