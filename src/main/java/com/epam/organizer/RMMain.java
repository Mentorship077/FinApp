package com.epam.organizer;

import com.epam.organizer.core.emp.StatusEmp;
import com.epam.organizer.core.rev.RevenueParser;
import com.epam.organizer.core.rmSheet.RMSheet;
import com.epam.organizer.models.customer.Customers;
import com.epam.organizer.models.rm.RMPersonList;

import java.util.List;

import static com.epam.organizer.commons.CommonConst.OUTPUT_DIRECTORY;
import static com.epam.organizer.commons.CommonConst.REVENUE_PATH;
import static com.epam.organizer.core.utils.Utils.copyFileUsingApacheCommonsIO;
import static com.epam.organizer.core.utils.Utils.getExcelPath;

public class RMMain {
    public static void main(String[] args) {
        copyFileUsingApacheCommonsIO(getExcelPath(), OUTPUT_DIRECTORY);
        REVENUE_PATH = OUTPUT_DIRECTORY + "\\" + getExcelPath();

        RevenueParser parser = new RevenueParser();
        StatusEmp statusEmp = new StatusEmp();
        RMSheet rmSheet = new RMSheet();

        List<Customers> customers = parser.getCustomerModel();

        statusEmp.assignTitleToEmployeeAndFixedRev(customers);
        statusEmp.setFixedRev(customers);

        List<RMPersonList> list = rmSheet.processAllEmployees(customers);
        rmSheet.writeSheetRMs(list);
    }
}
