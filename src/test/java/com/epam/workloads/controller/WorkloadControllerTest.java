package com.epam.workloads.controller;

import com.epam.workloads.dto.request.WorkloadRequestDTO;
import com.epam.workloads.dto.response.TrainerWorkloadResponseDTO;
import com.epam.workloads.service.WorkloadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jdmon on 12/01/2026
 * @project workloads-service
 */
class WorkloadControllerTest {

    private MockMvc mockMvc;
    private WorkloadService workloadService;

    @BeforeEach
    void setUp() {
        workloadService = mock(WorkloadService.class);
        WorkloadController controller = new WorkloadController(workloadService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


//    @Test
//    void updateWorkloadShouldReturnOk() throws Exception {
//        String json = """
//            {
//              "username":"jdoe",
//              "firstName":"John",
//              "lastName":"Doe",
//              "status":true,
//              "trainingDate":"2026-01-10",
//              "duration":60,
//              "actionType":"ADD"
//            }
//            """;
//
//        mockMvc.perform(post("/workloads")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isOk());
//
//        verify(workloadService).processWorkload(any(WorkloadRequestDTO.class));
//    }
//
//    @Test
//    void updateWorkloadShouldReturnBadRequestForInvalidDto() throws Exception {
//        String json = """
//            {
//              "username":"jdoe",
//              "firstName":"John",
//              "lastName":"Doe",
//              "status":true,
//              "trainingDate":"2026-01-10",
//              "duration":10,
//              "actionType":"ADD"
//            }
//            """;
//
//        mockMvc.perform(post("/workloads")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest());
//    }

    @Test
    void getTrainerWorkloadShouldReturnDto() throws Exception {
        TrainerWorkloadResponseDTO dto = new TrainerWorkloadResponseDTO(
                "jdoe", "John", "Doe", "true", List.of()
        );

        Mockito.when(workloadService.getTrainerWorkLoad("jdoe")).thenReturn(dto);

        mockMvc.perform(get("/workloads/jdoe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("jdoe"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

}