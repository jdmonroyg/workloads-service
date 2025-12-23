package com.epam.workloads.model;

import jakarta.persistence.*;

import java.util.List;

/**
 * @author jdmon on 18/12/2025
 * @project workloads-service
 */
@Entity
@Table(name = "trainer_workload")
public class TrainerWorkload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<YearlySummary> years;

    public TrainerWorkload() {
    }

    public TrainerWorkload(String username, String firstName, String lastName, Boolean status, List<YearlySummary> years) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.years = years;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<YearlySummary> getYears() {
        return years;
    }

    public void setYears(List<YearlySummary> years) {
        this.years = years;
    }
}
