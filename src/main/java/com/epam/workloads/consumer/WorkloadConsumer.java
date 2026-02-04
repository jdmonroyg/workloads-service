package com.epam.workloads.consumer;

import com.epam.workloads.dto.request.WorkloadRequestDTO;
import com.epam.workloads.service.WorkloadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

/**
 * @author jdmon on 12/01/2026
 * @project workloads-service
 */
@Component
public class WorkloadConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkloadConsumer.class);
    private final WorkloadService workloadService;
    private final ObjectMapper objectMapper;

    public WorkloadConsumer(WorkloadService workloadService, ObjectMapper objectMapper) {
        this.workloadService = workloadService;
        this.objectMapper = objectMapper;
    }

    @JmsListener(destination = "workload.queue")
    public void receiveWorkloadUpdate(String jsonMessage,
                                      @Header(value = "transactionId", required = false) String transactionId) {

        try {
            if (transactionId != null) {
                MDC.put("transactionId", transactionId);
            }
            WorkloadRequestDTO request = objectMapper.readValue(jsonMessage, WorkloadRequestDTO.class);

            if (request.duration() == 20) {
                //Active DLQ, when the duration is 20 min
                LOGGER.error("Invalid message: Missing required fields.");
                throw new IllegalArgumentException("Message validation failed: duration");
            }

            LOGGER.info("ActiveMQ Message Received. Action: {} for User: {}", request.actionType(), request.username());

            workloadService.processWorkload(request);

            LOGGER.info("Message processed successfully.");

        } catch (Exception e) {
            LOGGER.error("Error processing message from queue: {}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            MDC.clear();
        }
    }
}