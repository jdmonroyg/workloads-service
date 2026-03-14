package com.epam.workloads.cucumber.steps;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author jdmon on 16/02/2026
 * @project workloads-service
 */
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "jwt.secret=a_test_secret_for_cucumber_test_123456"
        })
public class CucumberSpringConfig {
}
