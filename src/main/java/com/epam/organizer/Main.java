package com.epam.organizer;


import com.epam.organizer.core.emp.StatusEmp;
import com.epam.organizer.core.managersSheet.RevenueSheetWriter;
import com.epam.organizer.core.rev.RevenueParser;
import com.epam.organizer.models.Customers;

import java.util.List;

import static com.epam.organizer.commons.CommonConst.OUTPUT_DIRECTORY;
import static com.epam.organizer.commons.CommonConst.REVENUE_PATH;
import static com.epam.organizer.core.utils.Utils.copyFileUsingApacheCommonsIO;
import static com.epam.organizer.core.utils.Utils.getExcelPath;

public class Main {
    public static void main(String[] args) {
        copyFileUsingApacheCommonsIO(getExcelPath(), OUTPUT_DIRECTORY);
        REVENUE_PATH = OUTPUT_DIRECTORY + "\\" + getExcelPath();

        RevenueParser parser = new RevenueParser();
        RevenueSheetWriter revenueSheetWriter = new RevenueSheetWriter();
        StatusEmp statusEmp = new StatusEmp();


        List<Customers> customers = parser.getCustomerModel();

        statusEmp.assignTitleToEmployeeAndFixedRev(customers);
        statusEmp.setFixedRev(customers);

        revenueSheetWriter.writeSheetEmp(customers);
        revenueSheetWriter.writeLevelsRevenue();
    }
}