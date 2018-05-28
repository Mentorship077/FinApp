package com.epam.organizer;

import com.epam.organizer.core.emp.EmployeeBhv;
import com.epam.organizer.core.emp.StatusEmp;
import com.epam.organizer.core.rev.RevenueParser;
import com.epam.organizer.core.rmSheet.RMSheet;
import com.epam.organizer.models.customer.Customers;
import com.epam.organizer.models.rm.FullEmployee;
import com.epam.organizer.models.rm.RMPersonList;

import java.util.List;

import static com.epam.organizer.commons.CommonConst.OUTPUT_DIRECTORY;
import static com.epam.organizer.commons.CommonConst.REVENUE_PATH;
import static com.epam.organizer.core.utils.Utils.copyFileUsingApacheCommonsIO;
import static com.epam.organizer.core.utils.Utils.getExcelPath;

public class RMMain {
    public static void main(String[] args) {
        REVENUE_PATH = OUTPUT_DIRECTORY + "\\" + getExcelPath();

        RMSheet rmSheet = new RMSheet();
        List<FullEmployee> empList = new EmployeeBhv().getEmployeeList();

        List<RMPersonList> list = rmSheet.processAllEmployees(empList);
        rmSheet.writeSheetRMs(list);
    }
}
