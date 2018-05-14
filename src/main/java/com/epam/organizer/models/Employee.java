package com.epam.organizer.models;

public class Employee extends DescriptionModel{
    private String title;
    private Integer employeeCount;
    private boolean finalRevenue;

    public boolean isFinalRevenue() {
        return finalRevenue;
    }

    public void setFinalRevenue(boolean finalRevenue) {
        this.finalRevenue = finalRevenue;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
        }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "title='" + title + '\'' +
                ", employeeCount=" + employeeCount +
                ", rowLabels='" + rowLabels + '\'' +
                ", reportedHours='" + reportedHours + '\'' +
                ", revenue='" + revenue + '\'' +
                ", effectiveRate='" + effectiveRate + '\'' +
                '}';
    }
}

