package com.epam.workloads.model;

import jakarta.persistence.*;

/**
 * @author jdmon on 19/12/2025
 * @project workloads-service
 */
@Entity
@Table(name = "monthly_summary")
public class MonthlySummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "month_number", nullable = false)
    private int monthNumber;

    private int trainingSummaryDuration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yearly_summary_id")
    private YearlySummary yearlySummary;

    public MonthlySummary() {
    }

    public MonthlySummary(int monthNumber, int trainingSummaryDuration, YearlySummary yearlySummary) {
        this.monthNumber = monthNumber;
        this.trainingSummaryDuration = trainingSummaryDuration;
        this.yearlySummary = yearlySummary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
    }

    public int getTrainingSummaryDuration() {
        return trainingSummaryDuration;
    }

    public void setTrainingSummaryDuration(int trainingSummaryDuration) {
        this.trainingSummaryDuration = trainingSummaryDuration;
    }

    public YearlySummary getYearlySummary() {
        return yearlySummary;
    }

    public void setYearlySummary(YearlySummary yearlySummary) {
        this.yearlySummary = yearlySummary;
    }
}
