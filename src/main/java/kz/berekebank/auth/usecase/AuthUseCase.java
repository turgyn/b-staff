package kz.berekebank.auth.usecase;

import kz.berekebank.auth.AuthUtil;
import kz.berekebank.auth.controller.dto.LoginRequest;
import kz.berekebank.auth.controller.dto.MessageResponse;
import kz.berekebank.auth.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthUseCase {

    private final UserRepository userRepository;
    private final AuthUtil authUtil;

    public ResponseEntity<?> execute(LoginRequest loginRequest) {
        log.info("starting signin: " + loginRequest.getUsername());
        if (!userRepository.existsByPhoneNumber(loginRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("username not found"));
        }
        return authUtil.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
    }
}
