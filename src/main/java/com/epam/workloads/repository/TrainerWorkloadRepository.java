package com.epam.workloads.repository;

import com.epam.workloads.model.TrainerWorkload;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

/**
 * @author jdmon on 18/12/2025
 * @project workloads-service
 */
public interface TrainerWorkloadRepository extends JpaRepository<TrainerWorkload, Long> {
    Optional<TrainerWorkload> findByUsername(String username);
}
