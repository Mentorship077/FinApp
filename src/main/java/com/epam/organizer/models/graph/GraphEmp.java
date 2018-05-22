package com.epam.organizer.models.graph;

public class GraphEmp {
    private String project;
    private Integer seniorityPerStream;
    private Double projectPM;
    private Integer empCount;

    public GraphEmp(String project, Integer seniorityPerStream, Double projectPM, Integer empCount) {
        this.project = project;
        this.seniorityPerStream = seniorityPerStream;
        this.projectPM = projectPM;
        this.empCount = empCount;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Integer getSeniorityPerStream() {
        return seniorityPerStream;
    }

    public void setSeniorityPerStream(Integer seniorityPerStream) {
        this.seniorityPerStream = seniorityPerStream;
    }

    public Double getProjectPM() {
        return projectPM;
    }

    public void setProjectPM(Double projectPM) {
        this.projectPM = projectPM;
    }

    public Integer getEmpCount() {
        return empCount;
    }

    public void setEmpCount(Integer empCount) {
        this.empCount = empCount;
    }
}
