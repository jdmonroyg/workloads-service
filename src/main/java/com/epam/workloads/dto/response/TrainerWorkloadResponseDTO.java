package com.epam.workloads.dto.response;

import java.util.List;

/**
 * @author jdmon on 18/12/2025
 * @project workloads-service
 */
public record TrainerWorkloadResponseDTO(
        String username,
        String firstName,
        String lastName,
        String status,
        List<YearlySummaryResponseDTO> years
        ){
}
