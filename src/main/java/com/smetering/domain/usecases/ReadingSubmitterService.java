package com.smetering.domain.usecases;

import com.smetering.data.repositories.GenericRepository;
import com.smetering.domain.entities.User;

import java.util.UUID;

public class ReadingSubmitterService {
    private final GenericRepository<User, UUID> repository;

    public ReadingSubmitterService(GenericRepository<User, UUID> repository) {
        this.repository = repository;
    }

}
