package com.example.Backend.services;

import com.example.Backend.model.ErrorMessage;
import com.example.Backend.repositories.ErrorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ErrorService {

    private final ErrorRepository errorRepository;

    public ErrorService(ErrorRepository errorRepository) {
        this.errorRepository = errorRepository;
    }

    public List<ErrorMessage> getAllErrors(Long userId) {
        return errorRepository.findByUserId(userId);
    }
}
