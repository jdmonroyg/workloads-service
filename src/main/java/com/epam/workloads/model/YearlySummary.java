package com.epam.workloads.model;

import java.util.List;

/**
 * @author jdmon on 19/12/2025
 * @project workloads-service
 */
public class YearlySummary {

    private int yearNumber;

    private List<MonthlySummary> months;

    public YearlySummary() {
    }

    public YearlySummary(int yearNumber, List<MonthlySummary> months) {
        this.yearNumber = yearNumber;
        this.months = months;
    }

    public int getYearNumber() {
        return yearNumber;
    }

    public void setYearNumber(int yearNumber) {
        this.yearNumber = yearNumber;
    }

    public List<MonthlySummary> getMonths() {
        return months;
    }

    public void setMonths(List<MonthlySummary> months) {
        this.months = months;
    }
}
