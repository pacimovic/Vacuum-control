package com.example.Backend.controllers;

import com.example.Backend.enums.Status;
import com.example.Backend.model.User;
import com.example.Backend.model.Vacuum;
import com.example.Backend.services.UserService;
import com.example.Backend.services.VacuumService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/vacuums")
public class VacuumController {

    private final VacuumService vacuumService;
    private final UserService userService;

    public VacuumController(VacuumService vacuumService, UserService userService) {
        this.vacuumService = vacuumService;
        this.userService = userService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createVacuum(@RequestBody Vacuum vacuum){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);
        vacuum.setUser(user);
        vacuum.setStatus(Status.STOPPED);
        vacuum.setActive(true);
        vacuum.setCreated(LocalDate.now());

        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("can_add_vacuum")))
            return ResponseEntity.ok(vacuumService.save(vacuum));

        return ResponseEntity.status(403).build();
    }


    @GetMapping(value = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchVacuum(@RequestParam String name, @RequestParam("status") List<Status> statuses,
                                          @RequestParam String dateFrom, @RequestParam String dateTo){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);

        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("can_search_vacuum")))
            return ResponseEntity.ok(vacuumService.searchVacuum(name, statuses, dateFrom, dateTo, user));

        return ResponseEntity.status(403).build();

    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> startVacuum(@PathVariable Long id) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());

        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("can_start_vacuum"))){
            Vacuum vacuum = this.vacuumService.startVacuum(id);
            if(vacuum != null)
            {
                return ResponseEntity.ok(vacuum);
            }
        }

        return ResponseEntity.status(403).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteVacuum(@PathVariable Long id) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());

        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("can_remove_vacuum"))){
            Optional<Vacuum> optionalVacuum = this.vacuumService.findById(id);
            if(optionalVacuum.isPresent()){
                Vacuum vacuum = optionalVacuum.get();

                if(vacuum.getStatus() == Status.STOPPED){
                    vacuum.setActive(false);
                    this.vacuumService.save(vacuum);
                    return ResponseEntity.noContent().build();
                }
            }

        }

        return ResponseEntity.status(403).build();
    }


}
