package com.epam.workloads.service;

import com.epam.workloads.dto.request.WorkloadRequestDTO;
import com.epam.workloads.dto.response.TrainerWorkloadResponseDTO;

/**
 * @author jdmon on 18/12/2025
 * @project workloads-service
 */
public interface WorkloadService {
    void processWorkload(WorkloadRequestDTO event);
    TrainerWorkloadResponseDTO getTrainerWorkLoad(String username);
}
