package com.example.Backend.repositories;

import com.example.Backend.enums.Status;
import com.example.Backend.model.User;
import com.example.Backend.model.Vacuum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacuumRepository extends JpaRepository<Vacuum, Long> {

    List<Vacuum> findByNameContainsAndStatusInAndUser(String name, List<Status> statuses, User user);

//    List<Vacuum> findAllByNameContainsAndStatusAndUser_Id(String name, Status status, Long user_id);


//    @Query("SELECT v FROM Vacuum v WHERE v.name LIKE CONCAT('%',:name,'%') AND v.status = ANY(:statuses) AND v.user = :user")
//    List<Vacuum> findAllBy(@Param("name") String name, @Param("statuses") List<Status> statuses, @Param("user") User user);
}
