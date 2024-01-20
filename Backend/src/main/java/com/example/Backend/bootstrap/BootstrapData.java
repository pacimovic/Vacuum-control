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

        User user1 = new User();
        user1.setName("Zika");
        user1.setSurname("Pavlovic");
        user1.setPassword(passwordEncoder.encode("1234"));
        user1.setEmail("zika@gmail.com");

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

        Permission permission1 = new Permission();
        permission1.setCan_create_users(true);
        permission1.setCan_read_users(true);
        permission1.setCan_update_users(true);
        permission1.setCan_delete_users(true);
        permission1.setCan_search_vacuum(true);
        permission1.setCan_start_vacuum(true);
        permission1.setCan_stop_vacuum(true);
        permission1.setCan_discharge_vacuum(true);
        permission1.setCan_add_vacuum(true);
        permission1.setCan_remove_vacuum(true);
        permission1.setUser(user1);


        user.setPermission(permission);
        user1.setPermission(permission1);

        List<Vacuum> vacuums = new ArrayList<>();
        for(int i = 1; i <= 3 ; i++){
            Vacuum vacuum = new Vacuum();
            vacuum.setName("Bosch_" + i);
            if(i%2 == 0) vacuum.setStatus(Status.ON);
            else vacuum.setStatus(Status.OFF);
            vacuum.setUser(user);
            vacuum.setActive(true);

            vacuums.add(vacuum);
        }

        List<Vacuum> vacuums1 = new ArrayList<>();
        for(int i = 1; i <= 3 ; i++){
            Vacuum vacuum1 = new Vacuum();
            vacuum1.setName("Kirbi_" + i);
            if(i%2 == 0) vacuum1.setStatus(Status.ON);
            else vacuum1.setStatus(Status.OFF);
            vacuum1.setUser(user1);
            vacuum1.setActive(true);

            vacuums1.add(vacuum1);
        }

        user.setVacuum(vacuums);
        user1.setVacuum(vacuums1);

        userRepository.save(user);
        userRepository.save(user1);
    }
}
