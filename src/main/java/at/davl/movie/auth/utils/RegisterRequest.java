package at.davl.movie.auth.utils;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * for registration, you need to send 4 parameters.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String name;
    private String email;
    private String username;
    private String password;
}
