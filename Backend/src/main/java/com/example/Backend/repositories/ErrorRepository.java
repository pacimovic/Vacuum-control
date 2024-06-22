package com.example.Backend.repositories;

import com.example.Backend.model.ErrorMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorRepository extends CrudRepository<ErrorMessage, Long> {
}
