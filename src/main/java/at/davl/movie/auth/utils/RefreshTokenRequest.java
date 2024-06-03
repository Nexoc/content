package at.davl.movie.auth.utils;

import lombok.Data;

@Data
public class RefreshTokenRequest {

    private String refreshToken;
}
