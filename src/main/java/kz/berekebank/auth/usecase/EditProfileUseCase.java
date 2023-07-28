package kz.berekebank.auth.usecase;

import kz.berekebank.auth.AuthUtil;
import kz.berekebank.auth.controller.dto.EditProfileRequest;
import kz.berekebank.auth.controller.dto.MessageResponse;
import kz.berekebank.auth.user.User;
import kz.berekebank.auth.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EditProfileUseCase {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final AuthUtil authUtil;

    public ResponseEntity<?> execute(EditProfileRequest editProfileRequest) {
        if (!AuthUtil.isLogged()) {
            return AuthUtil.notAuthorized();
        }

        String newPhoneNumber = editProfileRequest.getPhoneNumber();
        if (!newPhoneNumber.equals(AuthUtil.getCurUserPhoneNumber())) {
            // phoneNumber is edited
            if (userRepository.existsByPhoneNumber(editProfileRequest.getPhoneNumber())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("PhoneNumber is already taken!"));
            }
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(AuthUtil.getCurUserPhoneNumber(), editProfileRequest.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("password incorrect"));
        }

        User user = userRepository.findByPhoneNumber(AuthUtil.getCurUserPhoneNumber()).get();

        String curPassword = editProfileRequest.getPassword();
        user.setPhoneNumber(newPhoneNumber);
        user.setFullName(editProfileRequest.getFullName());
        if (!Strings.isBlank(editProfileRequest.getNewPassword())) {
            user.setPassword(encoder.encode(editProfileRequest.getNewPassword()));
            curPassword = editProfileRequest.getNewPassword();
        }
        userRepository.save(user);

        return authUtil.authenticate(newPhoneNumber, curPassword);
    }

}
