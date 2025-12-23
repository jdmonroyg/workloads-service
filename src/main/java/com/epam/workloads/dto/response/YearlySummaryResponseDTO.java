package com.epam.workloads.dto.response;

import java.util.List;

/**
 * @author jdmon on 19/12/2025
 * @project workloads-service
 */
public record YearlySummaryResponseDTO(
    int yearNumber,
    List<MonthlySummaryResponseDTO> months
){

}
