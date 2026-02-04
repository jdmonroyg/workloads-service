package com.epam.workloads.controller;

import com.epam.workloads.dto.request.WorkloadRequestDTO;
import com.epam.workloads.dto.response.TrainerWorkloadResponseDTO;
import com.epam.workloads.service.WorkloadService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author jdmon on 18/12/2025
 * @project workloads-service
 */
@RestController
@RequestMapping("/workloads")
public class WorkloadController {

    private final WorkloadService workloadService;

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkloadController.class);

    public WorkloadController(WorkloadService workloadService) {
        this.workloadService = workloadService;
    }

//    @PostMapping
//    public ResponseEntity<Void> updateWorkload(@RequestBody @Valid WorkloadRequestDTO event) {
//        LOGGER.info("Received workload event for trainer {} action {}", event.username(), event.actionType());
//        workloadService.processWorkload(event);
//        LOGGER.info("Workload event processed successfully");
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/{username}")
    public ResponseEntity<TrainerWorkloadResponseDTO> getTrainerWorkload(@PathVariable String username){
        LOGGER.info("Fetching workload data for trainer {}", username);
        TrainerWorkloadResponseDTO responseDTO = workloadService.getTrainerWorkLoad(username);
        return ResponseEntity.ok(responseDTO);
    }
}
