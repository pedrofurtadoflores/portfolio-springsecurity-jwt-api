package br.com.pedrofurtadoflores.springsecurityjwtapi.repository;

import br.com.pedrofurtadoflores.springsecurityjwtapi.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
