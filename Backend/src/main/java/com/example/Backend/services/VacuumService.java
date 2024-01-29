package com.example.Backend.services;

import com.example.Backend.enums.Status;
import com.example.Backend.model.User;
import com.example.Backend.model.Vacuum;
import com.example.Backend.repositories.VacuumRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VacuumService implements IService<Vacuum, Long>{

    private final VacuumRepository vacuumRepository;

    public VacuumService(VacuumRepository vacuumRepository) {
        this.vacuumRepository = vacuumRepository;
    }

    @Override
    public <S extends Vacuum> S save(S vacuum) {
        return this.vacuumRepository.save(vacuum);
    }

    @Override
    public Optional<Vacuum> findById(Long vacuumId) {
        return this.vacuumRepository.findById(vacuumId);
    }

    @Override
    public List<Vacuum> findAll() {
        return (List<Vacuum>) this.vacuumRepository.findAll();
    }

    @Override
    public void deleteById(Long vacuumId) {
        this.vacuumRepository.deleteById(vacuumId);
    }

    public Vacuum startVacuum(Long id) {
        Optional<Vacuum> optionalVacuum = this.vacuumRepository.findById(id);

        if(optionalVacuum.isPresent()){
            Vacuum vacuum = optionalVacuum.get();
            if(vacuum.getStatus().equals(Status.STOPPED)){
                try {
                    Thread.sleep(5000);

                    vacuum.setStatus(Status.RUNNING);
                    return this.vacuumRepository.save(vacuum);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ObjectOptimisticLockingFailureException exception) {
                    this.startVacuum(id);
                }
            }
        }

        return null;
    }


    public List<Vacuum> searchVacuum(String name, List<Status> statuses, String dateFrom, String dateTo, User user) {
        if(statuses.isEmpty()){
            statuses.add(Status.RUNNING);
            statuses.add(Status.STOPPED);
            statuses.add(Status.DISCHARGING);
        }

        LocalDate date1;
        if(dateFrom.equals("")) date1 = LocalDate.of(1970, 1, 1);
        else date1 = LocalDate.parse(dateFrom);

        if(dateTo != "") return this.vacuumRepository.findByNameContainsAndStatusInAndCreatedBetweenAndUser(name, statuses, date1, LocalDate.parse(dateTo), user);

        return this.vacuumRepository.findByNameContainsAndStatusInAndCreatedGreaterThanEqualAndUser(name, statuses, date1, user);
    }
}
