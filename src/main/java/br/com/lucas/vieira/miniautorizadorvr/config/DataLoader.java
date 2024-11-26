package br.com.lucas.vieira.miniautorizadorvr.config;

import br.com.lucas.vieira.miniautorizadorvr.entity.User;
import br.com.lucas.vieira.miniautorizadorvr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("username").isEmpty()) {
            User user = new User();
            user.setUsername("username");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRoles("USER");
            userRepository.save(user);
        }
    }
}

