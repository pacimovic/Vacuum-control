package com.example.Backend.services;

import com.example.Backend.enums.Status;
import com.example.Backend.model.User;
import com.example.Backend.model.Vacuum;
import com.example.Backend.repositories.VacuumRepository;
import org.springframework.stereotype.Service;

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

    public List<Vacuum> searchVacuum(String name, Status status, Long user) {
        return this.vacuumRepository.findAllByNameContainsAndStatusAndUser_Id(name, status, user);
    }
}
