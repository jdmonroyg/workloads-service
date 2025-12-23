package com.epam.workloads.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import static com.epam.workloads.util.Constants.*;

/**
 * @author jdmon on 18/12/2025
 * @project workloads-service
 */
public record WorkloadRequestDTO(

        @NotBlank(message = INVALID_FIELD)
        String username,

        @NotBlank(message = INVALID_FIELD)
        String firstName,

        @NotBlank(message = INVALID_FIELD)
        String lastName,

        @NotNull(message = INVALID_FIELD)
        Boolean status,

        @NotNull(message = INVALID_FIELD)
        LocalDate trainingDate,

        @Min(value = 20, message =INVALID_MIN_VALUE)
        @Max(value = 120, message = INVALID_MAX_VALUE )
        int duration, // minutes

        @NotNull(message = INVALID_FIELD)
        String actionType // "ADD" or "DELETE"
        ){
}
