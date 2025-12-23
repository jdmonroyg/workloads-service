package com.epam.workloads.model;

import jakarta.persistence.*;

import java.util.List;

/**
 * @author jdmon on 19/12/2025
 * @project workloads-service
 */
@Entity
@Table(name = "yearly_summary")
public class YearlySummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year_number", nullable = false)
    private int yearNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private TrainerWorkload trainer;

    @OneToMany(mappedBy = "yearlySummary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MonthlySummary> months;

    public YearlySummary() {
    }

    public YearlySummary(int yearNumber, TrainerWorkload trainer, List<MonthlySummary> months) {
        this.yearNumber = yearNumber;
        this.trainer = trainer;
        this.months = months;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYearNumber() {
        return yearNumber;
    }

    public void setYearNumber(int yearNumber) {
        this.yearNumber = yearNumber;
    }

    public TrainerWorkload getTrainer() {
        return trainer;
    }

    public void setTrainer(TrainerWorkload trainer) {
        this.trainer = trainer;
    }

    public List<MonthlySummary> getMonths() {
        return months;
    }

    public void setMonths(List<MonthlySummary> months) {
        this.months = months;
    }
}
