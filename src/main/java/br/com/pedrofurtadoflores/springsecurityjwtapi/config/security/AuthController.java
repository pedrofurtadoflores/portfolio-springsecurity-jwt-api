package br.com.pedrofurtadoflores.springsecurityjwtapi.config.security;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import br.com.pedrofurtadoflores.springsecurityjwtapi.dto.request.UserRequestDTO;
import br.com.pedrofurtadoflores.springsecurityjwtapi.model.User;
import br.com.pedrofurtadoflores.springsecurityjwtapi.repository.UserRepository;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/login")
        public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            String jwtToken = jwtService.generateToken(userDetails.getUsername(), Map.of());

            return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
        }

        @PostMapping("/register")
        public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody UserRequestDTO request) {
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.status(409).build(); // HTTP 409 Conflict
            }

            User user = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .gender(request.getGender())
                    .birthDate(request.getBirthDate())
                    .avatarUrl(request.getAvatarUrl())
                    .build();

            userRepository.save(user);

            String jwt = jwtService.generateToken(user.getEmail());
            return ResponseEntity.status(201).body(new AuthenticationResponse(jwt));
        }


}
