package at.davl.movie.controllers;

import at.davl.movie.auth.entities.ForgotPassword;
import at.davl.movie.auth.entities.User;
import at.davl.movie.auth.repositories.ForgotPasswordRepository;
import at.davl.movie.auth.repositories.UserRepository;
import at.davl.movie.auth.utils.ChangePassword;
import at.davl.movie.dto.MailBody;
import at.davl.movie.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    public ForgotPasswordController(UserRepository userRepository, EmailService emailService, ForgotPasswordRepository forgotPasswordRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // send mail for email verification
    @PostMapping("/verifyMail/{email}")
    public ResponseEntity<String> verifyMail(@PathVariable String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Please provide a valid email address"));

        Integer otp = otpGenerator();
        MailBody mailBody = MailBody
                .builder()
                .to(email)
                .text("this is the OTP for your forgot password request: " + otp)
                .subject("OTP for forgot password request")
                .build();

        ForgotPassword fp = ForgotPassword
                .builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000)) // 70 seconds
                .user(user)
                .build();

        emailService.sendSimpleMessage(mailBody);
        forgotPasswordRepository.save(fp);

        return ResponseEntity.ok("Email has been sent for forgot password request");
    }

    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp,
                                            @PathVariable String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid email address"));

        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp, user)
                .orElseThrow(() -> new RuntimeException("Invalid OTP for email: " + email));

        // if expiration time is done, over
        if(fp.getExpirationTime().before(Date.from(Instant.now()))){
            // TODO should be checked delete on Cascade works correct or not
            forgotPasswordRepository.deleteById(fp.getFpId());
            return new ResponseEntity<>("OTP has expired", HttpStatus.EXPECTATION_FAILED);
        }

        return ResponseEntity.ok("OTP is verified");

    }

    @PostMapping("/changePassword/{email}")
    public ResponseEntity<String> changePasswordHandler(
                                @RequestBody ChangePassword changePassword,
                                @PathVariable String email) {
        // if (pass == changed pass) are not equals
        if (!Objects.equals(changePassword.password(), changePassword.repeatedPassword())){
            return new ResponseEntity<>("Prease enter the password again", HttpStatus.EXPECTATION_FAILED);
        }

        String encodedPassword = passwordEncoder.encode(changePassword.password());
        userRepository.updatePasswordByEmail(email, encodedPassword);

        return ResponseEntity.ok("New password has been changed");

    }


    private Integer otpGenerator() {
        Random rand = new Random();
        return rand.nextInt(100_000, 999_999);
    }

}
