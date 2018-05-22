package com.epam.organizer.core.emp;

import com.epam.organizer.models.customer.Customers;
import com.epam.organizer.models.customer.Employee;
import com.epam.organizer.models.rm.FullEmployee;

import java.util.ArrayList;
import java.util.List;

import static com.epam.organizer.commons.CommonConst.*;

public class StatusEmp {
    private int number;
    private List<FullEmployee> empList = new EmployeeBhv().getEmployeeList();
    private List<FullEmployee> benchList = new ArrayList<>();

    public String getSeniority(String value) {
        String normValue;
        if (value.equals("Junior Software Test Automation Engineer")) {
            normValue = JUNIOR;
            number = 1;
        } else if (value.equals("Software Test Automation Engineer")) {
            normValue = INTERMEDIATE;
            number = 2;
        } else if (value.equals("Senior Software Test Automation Engineer") || value.equals("Senior Software Testing Engineer")) {
            normValue = SENIOR;
            number = 3;
        } else if (value.equals("Lead Software Test Automation Engineer") || value.equals("Software Engineering Team Leader")) {
            normValue = LEAD;
            number = 4;
        } else if (value.equals("Software Engineering Manager")) {
            normValue = ABOVE_LEAD;
            number = 5;
        } else {
            number = 0;
            normValue = "doesn't found employee";
        }
        return normValue;
    }


    public String findPersonTitle(String name, FullEmployee emp) {
        for (FullEmployee employee : empList) {
            if (name.equals(employee.getName())) {
                return getSeniority(employee.getTitle());
            }
        }
        if (name.equals(DOESN_T_FOUND_EMPLOYEE)) {
            benchList.add(emp);
        }
        return DOESN_T_FOUND_EMPLOYEE;
    }

    public String findPersonTitle(String name) {
        for (FullEmployee employee : empList) {
            if (name.equals(employee.getName())) {
                return getSeniority(employee.getTitle());
            }
        }
        return DOESN_T_FOUND_EMPLOYEE;
    }

    public List<FullEmployee> getBenchList() {
        return benchList;
    }

    public String findPersonRM(String name) {
        for (FullEmployee employee : empList) {
            if (name.equals(employee.getName())) {
                return employee.getRm();
            }
        }
        return DOESN_T_FOUND_RM;
    }


    public void assignTitleToEmployeeAndFixedRev(List<Customers> customers) {
        for (Customers customer : customers) {
            for (int j = 0; j < customer.getStreamsList().size(); j++) {
                for (int k = 0; k < customer.getStreamsList().get(j).getEmployeesList().size(); k++) {
                    String person = customer.getStreamsList().get(j).getEmployeesList().get(k).getRowLabels();
                    if (!findPersonTitle(person).isEmpty()) {
                        String titleResult = findPersonTitle(person);
                        String RM = findPersonRM(person);
                        customer.getStreamsList().get(j).getEmployeesList().get(k).setName(person);
                        customer.getStreamsList().get(j).getEmployeesList().get(k).setTitle(titleResult);
                        customer.getStreamsList().get(j).getEmployeesList().get(k).setRm(RM);
                        customer.getStreamsList().get(j).getEmployeesList().get(k).setEmployeeCount(number);
                    }
                }
            }
        }
    }


    public void setFixedRev(List<Customers> customers) {
        for (Customers customer : customers) {
            for (int j = 0; j < customer.getStreamsList().size(); j++) {

                for (int k = 0; k < customer.getStreamsList().get(j).getEmployeesList().size(); k++) {

                    Double reporterHourse = Double.valueOf(customer.getStreamsList().get(j).getEmployeesList().get(k).getReportedHours());
                    String seniorityLevel = customer.getStreamsList().get(j).getEmployeesList().get(k).getTitle();
                    Double revenue = Double.valueOf(customer.getStreamsList().get(j).getEmployeesList().get(k).getRevenue());

                    try {

                        Double reporterHourse1 = Double.valueOf(customer.getStreamsList().get(j).getEmployeesList().get(k + 1).getReportedHours());
                        String seniorityLevel1 = customer.getStreamsList().get(j).getEmployeesList().get(k + 1).getTitle();
                        Double revenue1 = Double.valueOf(customer.getStreamsList().get(j).getEmployeesList().get(k + 1).getRevenue());


                        if (!reporterHourse.equals(reporterHourse1) && seniorityLevel.equals(seniorityLevel1) && revenue.equals(revenue1)) {
                            Employee employee = customer.getStreamsList().get(j).getEmployeesList().get(k);
                            employee.setFinalRevenue(true);

                            Employee employee1 = customer.getStreamsList().get(j).getEmployeesList().get(k + 1);
                            employee1.setFinalRevenue(true);
                        }
                    } catch (IndexOutOfBoundsException exc) {
                        exc.getStackTrace();
                    }
                }
            }
        }
    }

}
