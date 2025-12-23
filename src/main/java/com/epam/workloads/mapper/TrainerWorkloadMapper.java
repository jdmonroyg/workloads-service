package com.epam.workloads.mapper;

import com.epam.workloads.dto.response.MonthlySummaryResponseDTO;
import com.epam.workloads.dto.response.TrainerWorkloadResponseDTO;
import com.epam.workloads.dto.response.YearlySummaryResponseDTO;
import com.epam.workloads.model.MonthlySummary;
import com.epam.workloads.model.TrainerWorkload;
import com.epam.workloads.model.YearlySummary;
import org.mapstruct.Mapper;

/**
 * @author jdmon on 21/12/2025
 * @project workloads-service
 */
@Mapper(componentModel = "spring")
public interface TrainerWorkloadMapper {

    TrainerWorkloadResponseDTO trainerToTrainerWorkloadResp(TrainerWorkload trainer);

    YearlySummaryResponseDTO yearlySummaryToYearlySummaryResponseDTO(YearlySummary yearlySummary);

    MonthlySummaryResponseDTO monthlySummaryToMonthlySummaryResponseDTO(MonthlySummary monthlySummary);
}
