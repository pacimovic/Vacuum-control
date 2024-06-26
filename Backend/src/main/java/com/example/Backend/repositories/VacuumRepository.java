package com.example.Backend.repositories;

import com.example.Backend.enums.Status;
import com.example.Backend.model.User;
import com.example.Backend.model.Vacuum;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VacuumRepository extends JpaRepository<Vacuum, Long> {

    List<Vacuum> findByNameContainsAndStatusInAndCreatedGreaterThanEqualAndActiveTrueAndUser(String name, List<Status> statuses, LocalDate dateFrom, User user);

    List<Vacuum> findByNameContainsAndStatusInAndCreatedBetweenAndActiveTrueAndUser(String name, List<Status> statuses, LocalDate dateFrom, LocalDate dateTo, User user);

    Optional<Vacuum> findByName(String name);

//    @Query("SELECT v FROM Vacuum v WHERE v.name LIKE CONCAT('%',:name,'%') AND v.status = ANY(:statuses) AND v.user = :user")
//    List<Vacuum> findAllBy(@Param("name") String name, @Param("statuses") List<Status> statuses, @Param("user") User user);
}
