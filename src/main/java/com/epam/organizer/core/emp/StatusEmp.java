package com.epam.organizer.core.emp;

import com.epam.organizer.models.Customers;
import com.epam.organizer.models.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.organizer.commons.CommonConst.*;

public class StatusEmp {
    private int number;
    private HashMap<String, String> empTitle = new EmployeeBhv().getEmployeeTitle();

    public String levelNormalize(String value) {
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


    public String filterMethod(String title) {

        for (Map.Entry<String, String> entry : empTitle.entrySet()) {
            if (title.equals(entry.getKey())) {
                return levelNormalize(entry.getValue());
            }
        }
        return DOESN_T_FOUND_EMPLOYEE;
    }

    public String filterMethodBench(String title) {

        for (Map.Entry<String, String> entry : empTitle.entrySet()) {
            if (title.equals(entry.getValue())) {
                return levelNormalize(entry.getValue());
            }
        }
        return DOESN_T_FOUND_EMPLOYEE;
    }

    public void assignTitleToEmployeeAndFixedRev(List<Customers> customers) {
        for (Customers customer : customers) {
            for (int j = 0; j < customer.getStreamsList().size(); j++) {
                for (int k = 0; k < customer.getStreamsList().get(j).getEmployeesList().size(); k++) {
                    String title = customer.getStreamsList().get(j).getEmployeesList().get(k).getRowLabels();
                    if (!filterMethod(title).isEmpty()) {
                        customer.getStreamsList().get(j).getEmployeesList().get(k).setTitle(filterMethod(title));
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
