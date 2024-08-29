package com.example.Backend.controllers;

import com.example.Backend.enums.Status;
import com.example.Backend.model.ScheduleDate;
import com.example.Backend.model.User;
import com.example.Backend.model.Vacuum;
import com.example.Backend.services.UserService;
import com.example.Backend.services.VacuumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/vacuums")
public class VacuumController {

    private final VacuumService vacuumService;
    private final UserService userService;

    public static Map<Long, Boolean> runningOperations = new HashMap<>();
    public static Map<Long, Integer> vacuumRunningCycle = new HashMap<>();
    public static Map<Long, List<ScheduleDate>> vacuumScheduleDatesOperation = new HashMap<>();

    private SimpMessagingTemplate simpMessagingTemplate;

    public VacuumController(VacuumService vacuumService, UserService userService, SimpMessagingTemplate simpMessagingTemplate) {
        this.vacuumService = vacuumService;
        this.userService = userService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createVacuum(@Valid @RequestBody Vacuum vacuum){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);
        vacuum.setUser(user);
        vacuum.setStatus(Status.STOPPED);
        vacuum.setActive(true);
        vacuum.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        Optional<Vacuum> optionalVacuum = this.vacuumService.findByName(vacuum.getName());
        if(optionalVacuum.isPresent()) return ResponseEntity.status(418).build();

        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("can_add_vacuum")) &&
                !vacuum.getName().equals(""))
            return ResponseEntity.ok(vacuumService.save(vacuum));

        return ResponseEntity.status(403).build();
    }


    @GetMapping(value = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchVacuum(@RequestParam String name, @RequestParam("status") List<Status> statuses,
                                          @RequestParam String dateFrom, @RequestParam String dateTo){



        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);

        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("can_search_vacuum")))
            return ResponseEntity.ok(vacuumService.searchVacuum(name, statuses, dateFrom, dateTo, user));

        return ResponseEntity.status(403).build();

    }

    @PutMapping(value = "/start/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> startVacuum(@PathVariable Long id) {
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("can_start_vacuum")) &&
                        !runningOperations.containsKey(id)){ //ako se neka operacija vec ne izvrsava nad datim usisivacem
            Optional<Vacuum> optionalVacuum = this.vacuumService.findById(id);
            if(optionalVacuum.isPresent() && optionalVacuum.get().isActive()){
                Vacuum vacuum = optionalVacuum.get();
                if(vacuum.getStatus().equals(Status.STOPPED) && // da li je u stanju STOPPED
                        vacuum.getUser().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){ //provera da li usisivac odgovara ulogovanom user-u
                    this.vacuumService.startVacuum(vacuum);
                    return ResponseEntity.ok().build();
                }
            }
            else return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(403).build();
    }

    @PutMapping(value = "/stop/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> stopVacuum(@PathVariable Long id){

        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("can_stop_vacuum")) &&
                    !runningOperations.containsKey(id)){
            Optional<Vacuum> optionalVacuum = this.vacuumService.findById(id);
            if(optionalVacuum.isPresent() && optionalVacuum.get().isActive()){
                Vacuum vacuum = optionalVacuum.get();
                if(vacuum.getStatus().equals(Status.RUNNING) &&
                        vacuum.getUser().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
                    this.vacuumService.stopVacuum(vacuum);
                    return ResponseEntity.ok().build();
                }
            }
            else return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(403).build();
    }

    @PutMapping(value = "/discharge/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> dischargeVacuum(@PathVariable Long id){

        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("can_discharge_vacuum")) &&
                !runningOperations.containsKey(id)){
            Optional<Vacuum> optionalVacuum = this.vacuumService.findById(id);
            if(optionalVacuum.isPresent() && optionalVacuum.get().isActive()){
                Vacuum vacuum = optionalVacuum.get();
                if(vacuum.getStatus().equals(Status.STOPPED) &&
                        vacuum.getUser().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
                    this.vacuumService.dischargeVacuum(vacuum);
                    return ResponseEntity.ok().build();
                }
            }
            else return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(403).build();
    }

    @PutMapping(value = "/schedule", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> scheduleOperation(@RequestBody ScheduleDate scheduleDate, @RequestParam Long id,
                                               @RequestParam String operation) {

        //Proveravamo da li je neka operacija nad usisivacem vec zakazana u ISTO VREME
        if(vacuumScheduleDatesOperation.containsKey(id)){
            List<ScheduleDate> scheduleDates = vacuumScheduleDatesOperation.get(id);
            for(ScheduleDate date: scheduleDates){
                if(date.compareTo(scheduleDate) == 0) return ResponseEntity.status(419).build();
            }
        }

        Collection<?> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        //Proveravamo permisije korisnika za operaciju koju hoce da zakaze
        if((operation.equals("start") && !authorities.contains(new SimpleGrantedAuthority("can_start_vacuum"))) ||
                (operation.equals("stop") && !authorities.contains(new SimpleGrantedAuthority("can_stop_vacuum"))) ||
                (operation.equals("discharge") && !authorities.contains(new SimpleGrantedAuthority("can_discharge_vacuum"))))
            return ResponseEntity.status(403).build();


        //Proveravamo da li je operacija jedna od datih
        if(!operation.equals("start") && !operation.equals("stop") && !operation.equals("discharge")){
            return ResponseEntity.status(403).build();
        }

        Optional<Vacuum> optionalVacuum = this.vacuumService.findById(id);
        if(optionalVacuum.isPresent() && optionalVacuum.get().isActive()){
            Vacuum vacuum = optionalVacuum.get();
            if(vacuum.getUser().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
                this.vacuumService.scheduleOperation(scheduleDate, operation, id);
                return ResponseEntity.ok().build();
            }
        }
        else return ResponseEntity.status(404).build();

        return ResponseEntity.status(403).build();
    }

    @GetMapping(value = "/{vacuumId}")
    public ResponseEntity<?> findVacuum(@PathVariable Long vacuumId){
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("can_search_vacuum"))){
            return ResponseEntity.ok(vacuumService.findById(vacuumId));
        }

        return ResponseEntity.status(403).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteVacuum(@PathVariable Long id) {

        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("can_remove_vacuum")) &&
                !runningOperations.containsKey(id)){
            Optional<Vacuum> optionalVacuum = this.vacuumService.findById(id);
            if(optionalVacuum.isPresent() && optionalVacuum.get().isActive()){
                Vacuum vacuum = optionalVacuum.get();
                if(vacuum.getStatus() == Status.STOPPED){
                    vacuum.setActive(false);
                    this.vacuumService.save(vacuum);
                    return ResponseEntity.noContent().build();
                }
            }
            else return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(403).build();
    }


}
