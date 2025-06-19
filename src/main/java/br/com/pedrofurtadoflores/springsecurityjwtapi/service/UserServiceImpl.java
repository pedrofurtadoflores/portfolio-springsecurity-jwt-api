package br.com.pedrofurtadoflores.springsecurityjwtapi.service;

import br.com.pedrofurtadoflores.springsecurityjwtapi.dto.request.UserRequestDTO;
import br.com.pedrofurtadoflores.springsecurityjwtapi.dto.response.UserResponseDTO;
import br.com.pedrofurtadoflores.springsecurityjwtapi.model.User;
import br.com.pedrofurtadoflores.springsecurityjwtapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return toResponseDTO(user);
    }

    @Override
    public UserResponseDTO create(UserRequestDTO dto) {
        User user = toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saved = repository.save(user);
        return toResponseDTO(saved);
    }

    @Override
    public UserResponseDTO update(Long id, UserRequestDTO dto) {
        User existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setGender(dto.getGender());
        existing.setBirthDate(dto.getBirthDate());
        existing.setAvatarUrl(dto.getAvatarUrl());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return toResponseDTO(repository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        repository.deleteById(id);
    }

    @Override
    public Optional<UserResponseDTO> getByEmail(String email) {
        return repository.findByEmail(email)
                .map(this::toResponseDTO);
    }


    private UserResponseDTO toResponseDTO(User entity) {
        return UserResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .gender(entity.getGender())
                .birthDate(entity.getBirthDate())
                .avatarUrl(entity.getAvatarUrl())
                .active(entity.getActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private User toEntity(UserRequestDTO dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .gender(dto.getGender())
                .birthDate(dto.getBirthDate())
                .avatarUrl(dto.getAvatarUrl())
                .build();
    }
}
