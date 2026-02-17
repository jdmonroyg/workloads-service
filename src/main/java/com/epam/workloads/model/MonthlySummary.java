package com.epam.workloads.model;

/**
 * @author jdmon on 19/12/2025
 * @project workloads-service
 */
public class MonthlySummary {

    private int monthNumber;

    private int trainingSummaryDuration;

    public MonthlySummary() {
    }

    public MonthlySummary(int monthNumber, int trainingSummaryDuration) {
        this.monthNumber = monthNumber;
        this.trainingSummaryDuration = trainingSummaryDuration;
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
}
