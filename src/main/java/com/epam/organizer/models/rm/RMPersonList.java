package com.epam.organizer.models.rm;

import com.epam.organizer.models.customer.Employee;

import java.util.List;

public class RMPersonList {
    private String rm;
    private List<Employee> employees;


    public RMPersonList(String rm, List<Employee> employees) {
        this.rm = rm;
        this.employees = employees;
    }

    public String getRm() {
        return rm;
    }

    public void setRm(String rm) {
        this.rm = rm;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
