package com.epam.workloads.mapper;

import com.epam.workloads.dto.response.MonthlySummaryResponseDTO;
import com.epam.workloads.dto.response.TrainerWorkloadResponseDTO;
import com.epam.workloads.dto.response.YearlySummaryResponseDTO;
import com.epam.workloads.model.MonthlySummary;
import com.epam.workloads.model.TrainerWorkload;
import com.epam.workloads.model.YearlySummary;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jdmon on 12/01/2026
 * @project workloads-service
 */
class TrainerWorkloadMapperTest {

    private final TrainerWorkloadMapper mapper = Mappers.getMapper(TrainerWorkloadMapper.class);

    @Test
    void shouldMapTrainerWorkloadToResponseDto() {
        TrainerWorkload trainer = new TrainerWorkload();
        trainer.setUsername("jdoe");
        trainer.setFirstName("John");
        trainer.setLastName("Doe");
        trainer.setStatus(true);

        YearlySummary yearly = new YearlySummary(2026, trainer, List.of());
        trainer.setYears(List.of(yearly));

        TrainerWorkloadResponseDTO dto = mapper.trainerToTrainerWorkloadResp(trainer);

        assertEquals("jdoe", dto.username());
        assertEquals("John", dto.firstName());
        assertEquals("Doe", dto.lastName());
        assertEquals("true", dto.status());
        assertEquals(1, dto.years().size());
        assertEquals(2026, dto.years().getFirst().yearNumber());
    }

    @Test
    void shouldMapYearlySummaryToResponseDto() {
        YearlySummary yearly = new YearlySummary(2026, null, List.of(
                new MonthlySummary(1, 60, null),
                new MonthlySummary(2, 90, null)
        ));

        YearlySummaryResponseDTO dto = mapper.yearlySummaryToYearlySummaryResponseDTO(yearly);

        assertEquals(2026, dto.yearNumber());
        assertEquals(2, dto.months().size());
        assertEquals(1, dto.months().getFirst().monthNumber());
        assertEquals(60, dto.months().getFirst().trainingSummaryDuration());
    }

    @Test
    void shouldMapMonthlySummaryToResponseDto() {
        MonthlySummary monthly = new MonthlySummary(3, 120, null);

        MonthlySummaryResponseDTO dto = mapper.monthlySummaryToMonthlySummaryResponseDTO(monthly);

        assertEquals(3, dto.monthNumber());
        assertEquals(120, dto.trainingSummaryDuration());
    }

}