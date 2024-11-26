package br.com.lucas.vieira.miniautorizadorvr.repository;

import br.com.lucas.vieira.miniautorizadorvr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
