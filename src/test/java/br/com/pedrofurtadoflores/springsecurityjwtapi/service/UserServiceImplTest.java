package br.com.pedrofurtadoflores.springsecurityjwtapi.service;

import br.com.pedrofurtadoflores.springsecurityjwtapi.dto.request.UserRequestDTO;
import br.com.pedrofurtadoflores.springsecurityjwtapi.dto.response.UserResponseDTO;
import br.com.pedrofurtadoflores.springsecurityjwtapi.model.User;
import br.com.pedrofurtadoflores.springsecurityjwtapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        User user = User.builder().id(1L).name("Pedro").email("pedro@email.com").build();
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserResponseDTO> result = userService.getAll();

        assertEquals(1, result.size());
        assertEquals("Pedro", result.get(0).getName());
    }

    @Test
    void testGetByIdSuccess() {
        User user = User.builder().id(1L).name("Pedro").email("pedro@email.com").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDTO result = userService.getById(1L);

        assertEquals("Pedro", result.getName());
    }

    @Test
    void testGetByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.getById(1L));
    }

    @Test
    void testCreateUser() {
        UserRequestDTO dto = UserRequestDTO.builder()
                .name("Pedro").email("pedro@email.com").password("123456").build();

        when(passwordEncoder.encode("123456")).thenReturn("encoded123456");
        when(userRepository.save(any(User.class))).thenAnswer(i -> {
            User u = i.getArgument(0);
            u.setId(1L);
            return u;
        });

        UserResponseDTO result = userService.create(dto);

        assertEquals("Pedro", result.getName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testUpdateUserSuccess() {
        User existing = User.builder().id(1L).name("Old").email("old@email.com").build();
        UserRequestDTO dto = UserRequestDTO.builder().name("New").email("new@email.com").password("abc123").build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(passwordEncoder.encode("abc123")).thenReturn("encodedABC123");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        UserResponseDTO result = userService.update(1L, dto);

        assertEquals("New", result.getName());
        assertEquals("new@email.com", result.getEmail());
    }

    @Test
    void testUpdateUserNotFound() {
        UserRequestDTO dto = UserRequestDTO.builder().name("Pedro").build();
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.update(1L, dto));
    }

    @Test
    void testDeleteUserSuccess() {
        when(userRepository.existsById(1L)).thenReturn(true);
        userService.delete(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void testDeleteUserNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> userService.delete(1L));
    }

    @Test
    void testGetByEmailSuccess() {
        User user = User.builder().id(1L).name("Pedro").email("pedro@email.com").build();
        when(userRepository.findByEmail("pedro@email.com")).thenReturn(Optional.of(user));

        Optional<UserResponseDTO> result = userService.getByEmail("pedro@email.com");

        assertTrue(result.isPresent());
        assertEquals("Pedro", result.get().getName());
    }

    @Test
    void testGetByEmailNotFound() {
        when(userRepository.findByEmail("notfound@email.com")).thenReturn(Optional.empty());

        Optional<UserResponseDTO> result = userService.getByEmail("notfound@email.com");

        assertTrue(result.isEmpty());
    }
}
