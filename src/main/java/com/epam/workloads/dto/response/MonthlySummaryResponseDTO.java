package com.epam.workloads.dto.response;

/**
 * @author jdmon on 19/12/2025
 * @project workloads-service
 */
public record MonthlySummaryResponseDTO(
        int monthNumber,
        int trainingSummaryDuration
) {
}
