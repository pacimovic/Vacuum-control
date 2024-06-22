package com.example.Backend.services;

import com.example.Backend.controllers.VacuumController;
import com.example.Backend.enums.Status;
import com.example.Backend.model.ErrorMessage;
import com.example.Backend.model.ScheduleDate;
import com.example.Backend.model.User;
import com.example.Backend.model.Vacuum;
import com.example.Backend.repositories.ErrorRepository;
import com.example.Backend.repositories.VacuumRepository;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VacuumService implements IService<Vacuum, Long>{

    private final VacuumRepository vacuumRepository;

    private final ErrorRepository errorRepository;

    private TaskScheduler taskScheduler;

    public VacuumService(VacuumRepository vacuumRepository, ErrorRepository errorRepository, TaskScheduler taskScheduler) {
        this.vacuumRepository = vacuumRepository;
        this.errorRepository = errorRepository;
        this.taskScheduler = taskScheduler;
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

    public void scheduleOperation(ScheduleDate scheduleDate, String operation, Long id) {

        CronTrigger cronTrigger = new CronTrigger("0 * * * * *");
        this.taskScheduler.schedule(() -> {
            System.out.println("Scheduled operation...");

            ErrorMessage errorMessage = null;

            Optional<Vacuum> optionalVacuum = this.findById(id);
            if(optionalVacuum.isPresent()){
                Vacuum vacuum = optionalVacuum.get();
                if(!VacuumController.runningOperations.containsKey(id)) {
                    if(operation.equals("start") && vacuum.getStatus().equals(Status.STOPPED)){
                        this.startVacuum(vacuum);
                    }
                    else if(operation.equals("stop") && vacuum.getStatus().equals(Status.RUNNING)){
                        this.stopVacuum(vacuum);
                    }
                    else if(operation.equals("discharge") && vacuum.getStatus().equals(Status.STOPPED)){
                        this.dischargeVacuum(vacuum);
                    }
                    else{
                        String message = "Vacuum is not in corresponding state";
                        errorMessage = new ErrorMessage(LocalDate.now(), id, operation, message);
                        System.out.println("Vacuum is not in corresponding state");
                        //Vacuum is not in adequate state
                    }
                }
                else{
                    String message = "Vacuum is busy";
                    errorMessage = new ErrorMessage(LocalDate.now(), id, operation, message);
                    System.out.println("Vacuum is busy");
                    //Operation is running on given vacuum
                }
            }
            else{
                String message = "Vacuum is removed";
                errorMessage = new ErrorMessage(LocalDate.now(), id, operation, message);
                System.out.println("Vacuum is removed");
                //Vacuum is removed
            }

            if(errorMessage != null) {
                this.errorRepository.save(errorMessage);
            }

        }, cronTrigger);
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

        if(!dateTo.equals("")) return this.vacuumRepository.findByNameContainsAndStatusInAndCreatedBetweenAndActiveTrueAndUser(name, statuses, date1, LocalDate.parse(dateTo), user);

        return this.vacuumRepository.findByNameContainsAndStatusInAndCreatedGreaterThanEqualAndActiveTrueAndUser(name, statuses, date1, user);
    }
}
