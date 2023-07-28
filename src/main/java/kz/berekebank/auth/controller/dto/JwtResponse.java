package kz.berekebank.auth.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String jwt;
    private String fullName;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
}
