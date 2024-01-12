package com.example.Backend.bootstrap;

import com.example.Backend.model.Permission;
import com.example.Backend.model.User;
import com.example.Backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public BootstrapData(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        System.out.println("Loading data...");

        User user = new User();
        user.setName("Petar");
        user.setSurname("Acimovic");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setEmail("petar@gmail.com");

        Permission permission = new Permission();
        permission.setCan_create_users(true);
        permission.setCan_read_users(true);
        permission.setCan_update_users(true);
        permission.setCan_delete_users(true);
        user.setPermission(permission);

        userRepository.save(user);
    }
}
