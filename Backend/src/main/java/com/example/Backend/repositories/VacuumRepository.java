package com.example.Backend.repositories;

import com.example.Backend.model.Vacuum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacuumRepository extends CrudRepository<Vacuum, Long> {
}
