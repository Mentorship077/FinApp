package com.epam.organizer;

import com.epam.organizer.core.managersSheet.ProjectPMFunctionality;
import com.epam.organizer.core.rev.RevenueParser;
import com.epam.organizer.models.Customers;

import java.util.List;

import static com.epam.organizer.commons.CommonConst.OUTPUT_DIRECTORY;
import static com.epam.organizer.commons.CommonConst.REVENUE_PATH;
import static com.epam.organizer.core.utils.Utils.getExcelPath;

public class MainPM {

    public static void main(String[] args) {
        REVENUE_PATH = OUTPUT_DIRECTORY + "\\" + getExcelPath();
        RevenueParser parser = new RevenueParser();
        ProjectPMFunctionality functionality = new ProjectPMFunctionality();


        List<Customers> customers = parser.getCustomerModel();
        functionality.setPM(customers);
    }
}
