Feature: Workload Management in MS2
    As a system administrator
    I want the service to process training minutes
    So that the trainer's monthly summary is updated

    Scenario: Successfully add training duration for a new trainer
        Given the database is empty for user "Jesus.Perez"
        When I send a workload request for "Jesus.Perez" with 100 minutes
        Then the trainer "Jesus.Perez" should exist in the database
        And the total duration for the current month should be 100

    Scenario: Invalid request should be rejected (Negative Scenario)
        When I send an invalid workload request with missing duration
        Then the system should not save any data for "invalid.user"

    Scenario: Successfully add training duration for a existing trainer in existing month
        Given a trainer "Jesus.Perez" exists with 60 minutes in February 2026
        When I add 60 minutes of workload for "Jesus.Perez" on "2026-02-20"
        Then the trainer should have 120 total minutes in February 2026

    Scenario: Delete workload for an existing trainer in an existing month
        Given a trainer "Maria.Sanchez" exists with 180 minutes in March 2026
        When I delete 60 minutes of workload for "Maria.Sanchez" on "2026-03-15"
        Then the trainer should have 120 total minutes in March 2026
        And March 2026 should still exist for trainer "Maria.Sanchez"
        And the year 2026 should still exist for trainer "Maria.Sanchez"

    Scenario: Delete workload that removes the last month and year for a trainer
        Given a trainer "Carlos.Mendez" exists with 200 minutes in November 2025
        And the trainer currently has no other months in 2025
        When I delete 200 minutes of workload for "Carlos.Mendez" on "2025-11-20"
        Then the trainer should have no months in 2025
        And the trainer should have no years recorded
        And the trainer record should still exist





