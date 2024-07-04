package com.example.Backend.repositories;

import com.example.Backend.model.ErrorMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ErrorRepository extends JpaRepository<ErrorMessage, Long> {

    List<ErrorMessage> findByUserId(Long userId);

}
