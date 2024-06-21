package at.davl.main.controllers;


import at.davl.main.auth.entities.RefreshToken;
import at.davl.main.auth.entities.User;
import at.davl.main.auth.services.AuthService;
import at.davl.main.auth.services.JwtService;
import at.davl.main.auth.services.RefreshTokenService;
import at.davl.main.auth.utils.AuthResponse;
import at.davl.main.auth.utils.LoginRequest;
import at.davl.main.auth.utils.RefreshTokenRequest;
import at.davl.main.auth.utils.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JwtService jwtService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @CrossOrigin(origins = "#{corsConfig.allowedOrigin}")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }


    @CrossOrigin(origins = "#{corsConfig.allowedOrigin}")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @CrossOrigin(origins = "#{corsConfig.allowedOrigin}")
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
        User user = refreshToken.getUser();

        String accessToken = jwtService.generateToken(user);

        return ResponseEntity.ok(AuthResponse
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build());
    }
}



















