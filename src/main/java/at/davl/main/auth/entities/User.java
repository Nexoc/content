package at.davl.main.auth.entities;

import at.davl.main.models.Folder;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @NotBlank(message = "The name field can't be blank")
    private String name;

    @NotBlank(message = "The username field can't be blank")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "The  email field can't be blank")
    @Column(unique = true)
    @Email(message = "Please enter email in proper format")
    private String email;

    @NotBlank(message = "The password field can't be blank")
    @Column(unique = true)
    @Size(min = 5, message = "The password must be at least 5 characters")
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private RefreshToken refreshToken;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private ForgotPassword forgotPassword;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
    private Set<Folder> folders;

    public String getUserNickname() { return username; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
}
