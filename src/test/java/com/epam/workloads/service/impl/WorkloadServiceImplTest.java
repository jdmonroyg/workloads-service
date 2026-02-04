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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
@ExtendWith(MockitoExtension.class)
class WorkloadServiceImplTest {

    @Mock
    private TrainerWorkloadRepository repository;

    @Mock
    private TrainerWorkloadMapper mapper;

    @InjectMocks
    private WorkloadServiceImpl workloadService;

    @Test
    void processWorkload_ShouldCreateNewTrainer_WhenTrainerDoesNotExist() {
        // Arrange
        WorkloadRequestDTO request = new WorkloadRequestDTO(
                "user.1", "Juan", "Perez", true,
                LocalDate.of(2025, 1, 10), 100, "ADD"
        );

        when(repository.findByUsername("user.1")).thenReturn(Optional.empty());

        // Act
        workloadService.processWorkload(request);

        // Assert
        verify(repository).save(argThat(trainer ->
                trainer.getUsername().equals("user.1") &&
                        trainer.getYears().size() == 1 &&
                        trainer.getYears().get(0).getMonths().get(0).getTrainingSummaryDuration() == 100
        ));
    }

    @Test
    void processWorkload_ShouldUpdateExistingMonth_WhenActionIsAdd() {
        // Arrange
        MonthlySummary month = new MonthlySummary(1, 50);
        YearlySummary year = new YearlySummary(2025, new ArrayList<>(List.of(month)));
        TrainerWorkload existingTrainer = new TrainerWorkload("user.1", "Juan", "Perez", true, new ArrayList<>(List.of(year)));

        WorkloadRequestDTO request = new WorkloadRequestDTO(
                "user.1", "Juan", "Perez", true,
                LocalDate.of(2025, 1, 10), 30, "ADD"
        );

        when(repository.findByUsername("user.1")).thenReturn(Optional.of(existingTrainer));

        // Act
        workloadService.processWorkload(request);

        // Assert
        verify(repository).save(argThat(trainer ->
                trainer.getYears().get(0).getMonths().get(0).getTrainingSummaryDuration() == 80 // 50 + 30
        ));
    }
}