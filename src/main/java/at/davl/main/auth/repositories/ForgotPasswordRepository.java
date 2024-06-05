package at.davl.main.auth.repositories;

import at.davl.main.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import at.davl.main.auth.entities.ForgotPassword;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {

    @Query("select fp from ForgotPassword fp where fp.otp = ?1 and fp.user = ?2")
    Optional<ForgotPassword> findByOtpAndUser(Integer opt, User user);
}
