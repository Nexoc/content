package at.davl.movie.auth.services;

import at.davl.movie.auth.utils.LoginRequest;

import at.davl.movie.auth.entities.User;
import at.davl.movie.auth.entities.UserRole;
import at.davl.movie.auth.repositories.UserRepository;
import at.davl.movie.auth.utils.AuthResponse;
import at.davl.movie.auth.utils.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest registerRequest) {
        // create user
        var user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                // password should be encoded
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                // by default role = USER
                .role(UserRole.USER)
                .build();

        // save user in DB
        User savedUser = userRepository.save(user);
        // create access token
        var accessToken = jwtService.generateToken(savedUser);
        // create refresh token
        var refreshToken = refreshTokenService.createRefreshToken(savedUser.getEmail());

        // add and return access and refresh tokens
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    public AuthResponse login(LoginRequest loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );


        var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        var accessToken = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(loginRequest.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();

    }


}
