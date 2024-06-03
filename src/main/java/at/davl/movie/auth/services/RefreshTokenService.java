package at.davl.movie.auth.services;


import at.davl.movie.auth.entities.RefreshToken;
import at.davl.movie.auth.entities.User;
import at.davl.movie.auth.repositories.RefreshTokenRepository;
import at.davl.movie.auth.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(String username) {
        // find user by email from DB
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User has not found by email: " + username));

        // get refresh token from user
        RefreshToken refreshToken = user.getRefreshToken();

        if(refreshToken == null) {
            long refreshTokenValidity = 5*60*60*10000; // 30 * 1000 = 30 sec
            refreshToken = RefreshToken.builder()
                    // create and add refresh token
                    .refreshToken(UUID.randomUUID().toString())
                    // add expiration time
                    .expirationTime(Instant.now().plusMillis(refreshTokenValidity))
                    // add user info
                    .user(user)
                    .build();
            refreshTokenRepository.save(refreshToken);
        }
        return refreshToken;
    }

    public RefreshToken verifyRefreshToken(String refreshToken) {
        RefreshToken refToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token has not found"));

        // checking if expiration time is not expiated
        if(refToken.getExpirationTime().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refToken);
            throw new RuntimeException("Refresh Token has already expired");
        }
        return refToken;
    }

}
