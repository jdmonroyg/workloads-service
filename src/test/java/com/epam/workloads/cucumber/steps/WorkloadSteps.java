package com.epam.workloads.cucumber.steps;

import com.epam.workloads.dto.request.WorkloadRequestDTO;
import com.epam.workloads.model.MonthlySummary;
import com.epam.workloads.model.TrainerWorkload;
import com.epam.workloads.model.YearlySummary;
import com.epam.workloads.repository.TrainerWorkloadRepository;
import com.epam.workloads.service.WorkloadService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author jdmon on 16/02/2026
 * @project workloads-service
 */
public class WorkloadSteps {

    @Autowired
    private WorkloadService workloadService;

    @Autowired
    private TrainerWorkloadRepository repository;

    private Exception exceptionThrown;

    private String lastUsername;

    // --- GIVEN STEPS ---

    @Given("the database is empty for user {string}")
    public void clean_database(String username) {
        repository.findByUsername(username).ifPresent(t -> repository.delete(t));
    }

    @Given("a trainer {string} exists with {int} minutes in {word} {int}")
    public void trainer_exists_with_data(String username, int minutes, String monthName, int yearNum) {
        clean_database(username);

        int monthNum = Month.valueOf(monthName.toUpperCase()).getValue();

        List<MonthlySummary> monthList = new ArrayList<>();
        monthList.add(new MonthlySummary(monthNum, minutes));

        YearlySummary year = new YearlySummary(yearNum, monthList);

        List<YearlySummary> yearList = new ArrayList<>();
        yearList.add(year);

        TrainerWorkload trainer = new TrainerWorkload(username, "Test", "User", true, yearList);

        repository.save(trainer);
    }

    @Given("the trainer currently has no other months in {int}")
    public void trainer_has_no_other_months(int yearNum) {

    }

    // --- WHEN STEPS ---

    @When("I send a workload request for {string} with {int} minutes")
    public void send_workload_request_minutes(String username, int minutes) {
        sendRequest(username, minutes, LocalDate.now(), "ADD");
    }

    @When("I send an invalid workload request with missing duration")
    public void send_invalid_request() {
        try {
            WorkloadRequestDTO request = new WorkloadRequestDTO(
                    "Invalid.User", "Inv", "Alid", true,
                    LocalDate.now(), 10, "ADD"
            );
            workloadService.processWorkload(request);
        } catch (Exception e) {
            this.exceptionThrown = e;
        }
    }

    @When("I add {int} minutes of workload for {string} on {string}")
    public void add_workload_specific_date(int minutes, String username, String dateStr) {
        sendRequest(username, minutes, LocalDate.parse(dateStr), "ADD");
    }

    @When("I delete {int} minutes of workload for {string} on {string}")
    public void delete_workload_specific_date(int minutes, String username, String dateStr) {
        sendRequest(username, minutes, LocalDate.parse(dateStr), "DELETE");
    }

    private void sendRequest(String username, int duration, LocalDate date, String action) {
        this.lastUsername = username;
        WorkloadRequestDTO request = new WorkloadRequestDTO(
                username, "First", "Last", true,
                date, duration, action
        );
        try {
            workloadService.processWorkload(request);
        } catch (Exception e) {
            this.exceptionThrown = e;
        }
    }

    // --- THEN STEPS ---
    @Then("the trainer {string} should exist in the database")
    public void trainer_should_exist(String username) {
        assertTrue(repository.findByUsername(username).isPresent());
    }

    @Then("the total duration for the current month should be {int}")
    public void verify_current_month_duration(int expectedDuration) {
        TrainerWorkload trainer = repository.findByUsername(lastUsername).orElseThrow();
        LocalDate now = LocalDate.now();

        int actual = getDurationFor(trainer, now.getYear(), now.getMonthValue());
        assertEquals(expectedDuration, actual);
    }

    @Then("the system should not save any data for {string}")
    public void system_should_not_save(String username) {
        assertTrue(repository.findByUsername(username).isEmpty());
    }

    @Then("the trainer should have {int} total minutes in {word} {int}")
    public void verify_specific_month_duration(int expected, String monthName, int year) {
        TrainerWorkload trainer = repository.findByUsername(lastUsername).orElseThrow();
        int monthNum = Month.valueOf(monthName.toUpperCase()).getValue();

        int actual = getDurationFor(trainer, year, monthNum);
        assertEquals(expected, actual, "The time is different from the current month duration");
    }

    @Then("{word} {int} should still exist for trainer {string}")
    public void month_should_still_exist(String monthName, int yearNum, String username) {
        TrainerWorkload trainer = repository.findByUsername(username).orElseThrow();
        int monthNum = Month.valueOf(monthName.toUpperCase()).getValue();

        boolean exists = trainer.getYears().stream()
                .filter(y -> y.getYearNumber() == yearNum)
                .flatMap(y -> y.getMonths().stream())
                .anyMatch(m -> m.getMonthNumber() == monthNum);

        assertTrue(exists, "The month does not exist");
    }

    @Then("the year {int} should still exist for trainer {string}")
    public void year_should_still_exist(int yearNum, String username) {
        TrainerWorkload trainer = repository.findByUsername(username).orElseThrow();
        boolean exists = trainer.getYears().stream()
                .anyMatch(y -> y.getYearNumber() == yearNum);
        assertTrue(exists, "The year does not exist");
    }

    @Then("the trainer should have no months in {int}")
    public void verify_no_months_in_year(int yearNum) {
        TrainerWorkload trainer = repository.findByUsername(lastUsername).orElseThrow();

        trainer.getYears().stream()
                .filter(y -> y.getYearNumber() == yearNum)
                .findFirst()
                .ifPresent(year -> assertTrue(year.getMonths().isEmpty(), "The list contains no months"));
    }

    @Then("the trainer should have no years recorded")
    public void verify_no_years() {
        TrainerWorkload trainer = repository.findByUsername(lastUsername).orElseThrow();
        assertTrue(trainer.getYears().isEmpty(), "the years recorded does not exist");
    }

    @Then("the trainer record should still exist")
    public void verify_record_exists() {
        assertTrue(repository.findByUsername(lastUsername).isPresent());
    }

    // Helper
    private int getDurationFor(TrainerWorkload t, int year, int month) {
        return t.getYears().stream()
                .filter(y -> y.getYearNumber() == year)
                .flatMap(y -> y.getMonths().stream())
                .filter(m -> m.getMonthNumber() == month)
                .mapToInt(MonthlySummary::getTrainingSummaryDuration)
                .findFirst()
                .orElse(0);
    }


}
