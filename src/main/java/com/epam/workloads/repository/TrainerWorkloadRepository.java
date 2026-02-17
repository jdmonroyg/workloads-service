package com.epam.workloads.repository;

import com.epam.workloads.model.TrainerWorkload;

import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.Optional;

/**
 * @author jdmon on 18/12/2025
 * @project workloads-service
 */
public interface TrainerWorkloadRepository extends MongoRepository<TrainerWorkload, String> {
    Optional<TrainerWorkload> findByUsername(String username);
}
