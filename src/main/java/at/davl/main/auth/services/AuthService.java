package at.davl.main.auth.services;

import at.davl.main.auth.utils.LoginRequest;

import at.davl.main.auth.entities.User;
import at.davl.main.auth.entities.UserRole;
import at.davl.main.auth.repositories.UserRepository;
import at.davl.main.auth.utils.AuthResponse;
import at.davl.main.auth.utils.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final RefreshTokenService refreshTokenService;
    @Autowired
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
                .userRole(String.valueOf(savedUser.getRole()))
                .username(savedUser.getUserNickname())
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
                .username(user.getUserNickname())
                .userRole(String.valueOf(user.getRole()))
                .build();
    }
}
