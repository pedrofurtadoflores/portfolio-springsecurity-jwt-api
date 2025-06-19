package br.com.pedrofurtadoflores.springsecurityjwtapi.service;

import br.com.pedrofurtadoflores.springsecurityjwtapi.dto.request.UserRequestDTO;
import br.com.pedrofurtadoflores.springsecurityjwtapi.dto.response.UserResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserResponseDTO> getAll();

    UserResponseDTO getById(Long id);

    UserResponseDTO create(UserRequestDTO dto);

    UserResponseDTO update(Long id, UserRequestDTO dto);

    void delete(Long id);

    Optional<UserResponseDTO> getByEmail(String email);
}
