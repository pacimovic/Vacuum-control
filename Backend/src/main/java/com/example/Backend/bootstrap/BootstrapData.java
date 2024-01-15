package com.example.Backend.bootstrap;

import com.example.Backend.enums.Status;
import com.example.Backend.model.Permission;
import com.example.Backend.model.User;
import com.example.Backend.model.Vacuum;
import com.example.Backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
        permission.setCan_search_vacuum(true);
        permission.setCan_start_vacuum(true);
        permission.setCan_stop_vacuum(true);
        permission.setCan_discharge_vacuum(true);
        permission.setCan_add_vacuum(true);
        permission.setCan_remove_vacuum(true);
        permission.setUser(user);

        user.setPermission(permission);

        List<Vacuum> vacuums = new ArrayList<>();
        for(int i = 1; i <= 5 ; i++){
            Vacuum vacuum = new Vacuum();
            vacuum.setName("Usisivac_" + i);
            vacuum.setStatus(Status.ON);
            vacuum.setUser(user);
            vacuum.setActive(false);

            vacuums.add(vacuum);
        }

        user.setVacuum(vacuums);

        userRepository.save(user);
    }
}
