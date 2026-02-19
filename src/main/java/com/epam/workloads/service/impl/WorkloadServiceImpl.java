package com.epam.workloads.service.impl;

import com.epam.workloads.dto.request.WorkloadRequestDTO;
import com.epam.workloads.dto.response.TrainerWorkloadResponseDTO;
import com.epam.workloads.exception.NotFoundException;
import com.epam.workloads.mapper.TrainerWorkloadMapper;
import com.epam.workloads.model.MonthlySummary;
import com.epam.workloads.model.TrainerWorkload;
import com.epam.workloads.model.YearlySummary;
import com.epam.workloads.repository.TrainerWorkloadRepository;
import com.epam.workloads.service.WorkloadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author jdmon on 18/12/2025
 * @project workloads-service
 */

@Service
public class WorkloadServiceImpl implements WorkloadService {

    private final TrainerWorkloadRepository twRepository;
    private final TrainerWorkloadMapper mapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkloadServiceImpl.class);

    public WorkloadServiceImpl(TrainerWorkloadRepository twRepository, TrainerWorkloadMapper mapper) {
        this.twRepository = twRepository;
        this.mapper = mapper;
    }

    @Override
    public void processWorkload(WorkloadRequestDTO request) {
        LOGGER.info("Processing workload event for trainer {} action {}", request.username(), request.actionType());

        TrainerWorkload trainer = twRepository.findByUsername(request.username())
                .orElseGet(() -> {
                    LOGGER.info("Trainer not found, creating new profile for {}",request.username());
                    return new TrainerWorkload(
                            request.username(),
                            request.firstName(),
                            request.lastName(),
                            request.status(),
                            new ArrayList<>()
                    );
                });

        LocalDate date = request.trainingDate();
        int yearNum = date.getYear();
        int monthNum = date.getMonthValue();

        YearlySummary yearlySummary = trainer.getYears().stream()
                .filter(y -> y.getYearNumber() == yearNum)
                .findFirst()
                .orElseGet( () -> {
                    YearlySummary y = new YearlySummary(yearNum, new ArrayList<>());
                    trainer.getYears().add(y);
                    return y;
                });

        MonthlySummary monthlySummary = yearlySummary.getMonths().stream()
                .filter( m -> m.getMonthNumber()==monthNum)
                .findFirst()
                .orElseGet( () -> {
                    MonthlySummary m = new MonthlySummary(monthNum, 0 );
                    yearlySummary.getMonths().add(m);
                    return m;
                });

        //minutes
        int currentDuration = monthlySummary.getTrainingSummaryDuration();
        int requestDuration = request.duration();

        switch (request.actionType().toUpperCase()) {
            case "ADD" -> monthlySummary
                    .setTrainingSummaryDuration(currentDuration + requestDuration);
            case "DELETE" -> monthlySummary
                    .setTrainingSummaryDuration(Math.max(currentDuration - requestDuration, 0));
            default -> throw new IllegalArgumentException("ActionType no supported: " + request.actionType());
        }

        if (monthlySummary.getTrainingSummaryDuration() == 0) {
            yearlySummary.getMonths().remove(monthlySummary);
        }

        if (yearlySummary.getMonths().isEmpty()) {
            trainer.getYears().remove(yearlySummary);
        }

        twRepository.save(trainer);
        LOGGER.info("Workload saved successfully");

    }

    @Override
    public TrainerWorkloadResponseDTO getTrainerWorkLoad(String username) {
        LOGGER.info("Getting workload for trainer {}", username);
        TrainerWorkload trainer = twRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Trainer not found"));
        return mapper.trainerToTrainerWorkloadResp(trainer);
    }
}
