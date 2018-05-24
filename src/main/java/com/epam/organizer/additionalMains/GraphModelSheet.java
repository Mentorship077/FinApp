package com.epam.organizer.additionalMains;

import com.epam.organizer.core.emp.StatusEmp;
import com.epam.organizer.core.graphDataSheet.GraphDataSheet;
import com.epam.organizer.core.managersSheet.ProjectPMFunctionality;
import com.epam.organizer.core.rev.RevenueParser;
import com.epam.organizer.models.customer.Customers;
import com.epam.organizer.models.graph.GraphEmp;

import java.util.List;

import static com.epam.organizer.commons.CommonConst.OUTPUT_DIRECTORY;
import static com.epam.organizer.commons.CommonConst.REVENUE_PATH;
import static com.epam.organizer.core.utils.Utils.getExcelPath;

public class GraphModelSheet {
    public static void main(String[] args) {
        REVENUE_PATH = OUTPUT_DIRECTORY + "\\" + getExcelPath();

        RevenueParser parser = new RevenueParser();
        GraphDataSheet graphEmpList = new GraphDataSheet();
        ProjectPMFunctionality functionality = new ProjectPMFunctionality();
        StatusEmp statusEmp = new StatusEmp();

        List<Customers> customers = parser.getCustomerModel();

        statusEmp.assignTitleToEmployeeAndFixedRev(customers);
        statusEmp.setFixedRev(customers);


        functionality.setPM(customers);
        List<GraphEmp> list = functionality.getGraphEmpList();
        graphEmpList.writeSheetGraphs(list);
    }
}
