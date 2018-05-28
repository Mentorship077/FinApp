package com.epam.organizer.core.managersSheet;

import com.epam.organizer.core.emp.EmployeeBhv;
import com.epam.organizer.models.rm.FullEmployee;

import java.util.List;

public abstract class BenchFunctionlity {
    List<FullEmployee> empList = new EmployeeBhv().getEmployeeList();

}
