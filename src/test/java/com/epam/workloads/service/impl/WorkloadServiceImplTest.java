package com.epam.workloads.service.impl;

import com.epam.workloads.dto.request.WorkloadRequestDTO;
import com.epam.workloads.dto.response.TrainerWorkloadResponseDTO;
import com.epam.workloads.exception.NotFoundException;
import com.epam.workloads.mapper.TrainerWorkloadMapper;
import com.epam.workloads.model.MonthlySummary;
import com.epam.workloads.model.TrainerWorkload;
import com.epam.workloads.model.YearlySummary;
import com.epam.workloads.repository.TrainerWorkloadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author jdmon on 12/01/2026
 * @project workloads-service
 */
class WorkloadServiceImplTest {
    private TrainerWorkloadRepository repository;
    private TrainerWorkloadMapper mapper;
    private WorkloadServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(TrainerWorkloadRepository.class);
        mapper = mock(TrainerWorkloadMapper.class);
        service = new WorkloadServiceImpl(repository, mapper);
    }

    @Test
    void processWorkloadShouldAddMinutes() {
        TrainerWorkload trainer = new TrainerWorkload();
        trainer.setUsername("jdoe");
        YearlySummary yearly = new YearlySummary(2026, trainer, new ArrayList<>());
        MonthlySummary monthly = new MonthlySummary(1, 30, yearly);
        yearly.setMonths(List.of(monthly));
        trainer.setYears(List.of(yearly));

        WorkloadRequestDTO dto = new WorkloadRequestDTO(
                "jdoe", "John", "Doe", true,
                LocalDate.of(2026,1,10), 60, "ADD"
        );

        when(repository.findByUsername("jdoe")).thenReturn(Optional.of(trainer));

        service.processWorkload(dto);

        assertEquals(90, monthly.getTrainingSummaryDuration());
        verify(repository).save(trainer);
    }

    @Test
    void processWorkloadShouldDeleteMinutesWithoutNegative() {
        TrainerWorkload trainer = new TrainerWorkload();
        trainer.setUsername("jdoe");
        YearlySummary yearly = new YearlySummary(2026, trainer, new ArrayList<>());
        MonthlySummary monthly = new MonthlySummary(1, 30, yearly);
        yearly.setMonths(List.of(monthly));
        trainer.setYears(List.of(yearly));

        WorkloadRequestDTO dto = new WorkloadRequestDTO(
                "jdoe", "John", "Doe", true,
                LocalDate.of(2026,1,10), 50, "DELETE"
        );

        when(repository.findByUsername("jdoe")).thenReturn(Optional.of(trainer));

        service.processWorkload(dto);

        assertEquals(0, monthly.getTrainingSummaryDuration()); // no negativo
        verify(repository).save(trainer);
    }

    @Test
    void processWorkloadShouldCreateNewTrainerIfNotExists() {
        WorkloadRequestDTO dto = new WorkloadRequestDTO(
                "newuser", "Jane", "Doe", true,
                LocalDate.of(2026,1,10), 40, "ADD"
        );

        when(repository.findByUsername("newuser")).thenReturn(Optional.empty());

        service.processWorkload(dto);

        verify(repository).save(any(TrainerWorkload.class));
    }

    @Test
    void processWorkloadShouldThrowForInvalidAction() {
        WorkloadRequestDTO dto = new WorkloadRequestDTO(
                "jdoe", "John", "Doe", true,
                LocalDate.of(2026,1,10), 40, "INVALID"
        );

        TrainerWorkload trainer = new TrainerWorkload();
        trainer.setUsername("jdoe");
        trainer.setYears(new ArrayList<>());


        when(repository.findByUsername("jdoe")).thenReturn(Optional.of(trainer));

        assertThrows(IllegalArgumentException.class, () -> service.processWorkload(dto));
    }

    @Test
    void getTrainerWorkLoadShouldReturnDto() {
        TrainerWorkload trainer = new TrainerWorkload();
        trainer.setUsername("jdoe");

        TrainerWorkloadResponseDTO dto = new TrainerWorkloadResponseDTO("jdoe","John","Doe","true", List.of());

        when(repository.findByUsername("jdoe")).thenReturn(Optional.of(trainer));
        when(mapper.trainerToTrainerWorkloadResp(trainer)).thenReturn(dto);

        TrainerWorkloadResponseDTO result = service.getTrainerWorkLoad("jdoe");

        assertEquals("jdoe", result.username());
    }

    @Test
    void getTrainerWorkLoadShouldThrowWhenNotFound() {
        when(repository.findByUsername("jdoe")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getTrainerWorkLoad("jdoe"));
    }


}