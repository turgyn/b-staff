package kz.berekebank.auth.usecase;

import kz.berekebank.auth.AuthUtil;
import kz.berekebank.auth.controller.dto.MessageResponse;
import kz.berekebank.auth.controller.dto.SignupRequest;
import kz.berekebank.auth.user.User;
import kz.berekebank.auth.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegistrationUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthUtil authUtil;

    public ResponseEntity<?> execute(SignupRequest signUpRequest) {
        if (userRepository.existsByPhoneNumber(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: username is already taken!"));
        }

        // Create new user's account
        User user = User.buildClient(
                signUpRequest.getUsername(),
                signUpRequest.getFullName(),
                encoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);

        return authUtil.authenticate(signUpRequest.getUsername(), signUpRequest.getPassword());
    }
}
