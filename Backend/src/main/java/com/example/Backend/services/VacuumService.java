package com.example.Backend.services;

import com.example.Backend.controllers.VacuumController;
import com.example.Backend.enums.Status;
import com.example.Backend.model.User;
import com.example.Backend.model.Vacuum;
import com.example.Backend.repositories.VacuumRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Async;
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


    @Async
    public void startVacuum(Vacuum vacuum) {
        try {
            if(vacuum.getStatus().equals(Status.STOPPED))
            {
                System.out.println("Starting...");
                VacuumController.runningOperations.put(vacuum.getId(), true);
                Thread.sleep(5000);
                vacuum.setStatus(Status.RUNNING);
                this.vacuumRepository.save(vacuum);
                VacuumController.runningOperations.remove(vacuum.getId());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ObjectOptimisticLockingFailureException exception) {
            this.startVacuum(vacuum);
        }
    }

    @Async
    public void stopVacuum(Vacuum vacuum) {
        try {
            if(vacuum.getStatus().equals(Status.RUNNING))
            {
                System.out.println("Stopping...");
                VacuumController.runningOperations.put(vacuum.getId(), true);
                Thread.sleep(5000);
                vacuum.setStatus(Status.STOPPED);
                Vacuum newVacuum = this.vacuumRepository.save(vacuum);
                int cycleCounter = 0;
                if(VacuumController.vacuumRunningCycle.containsKey(vacuum.getId()))
                    cycleCounter = VacuumController.vacuumRunningCycle.get(vacuum.getId());
                cycleCounter++;
                VacuumController.vacuumRunningCycle.put(vacuum.getId(), cycleCounter);
                if(cycleCounter >= 3){
                    VacuumController.vacuumRunningCycle.put(vacuum.getId(), 0);
                    dischargeVacuum(newVacuum);
                }
                VacuumController.runningOperations.remove(vacuum.getId());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ObjectOptimisticLockingFailureException exception) {
            this.stopVacuum(vacuum);
        }
    }

    @Async
    public void dischargeVacuum(Vacuum vacuum) {
        try {
            if(vacuum.getStatus().equals(Status.STOPPED)){
                System.out.println("Discharging...");
                VacuumController.runningOperations.put(vacuum.getId(), true);
                Thread.sleep(15000);
                vacuum.setStatus(Status.DISCHARGING);
                Vacuum newVacuum = this.vacuumRepository.save(vacuum);
                Thread.sleep(15000);
                newVacuum.setStatus(Status.STOPPED);
                this.vacuumRepository.save(newVacuum);
                VacuumController.runningOperations.remove(vacuum.getId());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ObjectOptimisticLockingFailureException exception) {
            this.dischargeVacuum(vacuum);
        }
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

        if(dateTo != "") return this.vacuumRepository.findByNameContainsAndStatusInAndCreatedBetweenAndActiveTrueAndUser(name, statuses, date1, LocalDate.parse(dateTo), user);

        return this.vacuumRepository.findByNameContainsAndStatusInAndCreatedGreaterThanEqualAndActiveTrueAndUser(name, statuses, date1, user);
    }
}
