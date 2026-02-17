package com.epam.workloads.mapper;

import com.epam.workloads.dto.response.MonthlySummaryResponseDTO;
import com.epam.workloads.dto.response.TrainerWorkloadResponseDTO;
import com.epam.workloads.dto.response.YearlySummaryResponseDTO;
import com.epam.workloads.model.MonthlySummary;
import com.epam.workloads.model.TrainerWorkload;
import com.epam.workloads.model.YearlySummary;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
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
        // Arrange
        TrainerWorkload trainer = new TrainerWorkload();
        trainer.setUsername("jdoe");
        trainer.setFirstName("John");
        trainer.setLastName("Doe");
        trainer.setStatus(true);

        YearlySummary yearly = new YearlySummary(2026, new ArrayList<>());
        trainer.setYears(List.of(yearly));

        // Act
        TrainerWorkloadResponseDTO dto = mapper.trainerToTrainerWorkloadResp(trainer);

        // Assert
        assertEquals("jdoe", dto.username());
        assertEquals("John", dto.firstName());
        assertEquals("Doe", dto.lastName());
        assertTrue(Boolean.parseBoolean(dto.status()));
        assertEquals(1, dto.years().size());
        assertEquals(2026, dto.years().getFirst().yearNumber());
    }

    @Test
    void shouldMapYearlySummaryToResponseDto() {
        // Arrange
        // Eliminamos los nulos de los constructores (ya no existen esos parámetros)
        YearlySummary yearly = new YearlySummary(2026, List.of(
                new MonthlySummary(1, 60),
                new MonthlySummary(2, 90)
        ));

        // Act
        YearlySummaryResponseDTO dto = mapper.yearlySummaryToYearlySummaryResponseDTO(yearly);

        // Assert
        assertEquals(2026, dto.yearNumber());
        assertEquals(2, dto.months().size());
        assertEquals(1, dto.months().getFirst().monthNumber());
        assertEquals(60, dto.months().getFirst().trainingSummaryDuration());
    }

    @Test
    void shouldMapMonthlySummaryToResponseDto() {
        // Arrange
        MonthlySummary monthly = new MonthlySummary(3, 120);

        // Act
        MonthlySummaryResponseDTO dto = mapper.monthlySummaryToMonthlySummaryResponseDTO(monthly);

        // Assert
        assertEquals(3, dto.monthNumber());
        assertEquals(120, dto.trainingSummaryDuration());
    }
}