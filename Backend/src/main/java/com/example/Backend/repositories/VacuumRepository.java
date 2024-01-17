package com.example.Backend.repositories;

import com.example.Backend.model.Vacuum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacuumRepository extends CrudRepository<Vacuum, Long> {

    List<Vacuum> findAllByNameContainsAndUser_Id(String name, Long user_id);
}
