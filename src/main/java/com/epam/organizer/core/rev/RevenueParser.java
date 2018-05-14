package com.epam.organizer.core.rev;


import com.epam.organizer.core.base.BaseExcel;
import com.epam.organizer.core.utils.ExcelFileUtils;
import com.epam.organizer.models.Customers;
import com.epam.organizer.models.Employee;
import com.epam.organizer.models.Streams;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

import static com.epam.organizer.commons.CommonConst.*;
import static com.epam.organizer.commons.NumberConstant.*;
import static com.epam.organizer.core.utils.Utils.isStream;


public class RevenueParser {
    public static int revenueRow = 10;
    private List<String> customerRowLabel = new CustomerParser().getCustomerRowLabel();
    private BaseExcel baseExcel = new BaseExcel(REVENUE_PATH).openFile();
    private Sheet sheet = baseExcel.getSheet(REVENUE_SHEET);


    public void createChartSheet() {
        baseExcel.createSheet(CHART);
        baseExcel.saveChangesToFile();
    }

    public void deleteEvaluationWarning() {

        int i = baseExcel.getWorkbook().getSheetIndex(EVALUATION_WARNING);
        if (i != -1) {
            baseExcel.getWorkbook().removeSheetAt(i);
        }
        baseExcel.saveChangesToFile();
    }

    public List<Customers> getCustomerModel() {
        List<Streams> streams = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        List<Customers> customers = new ArrayList<>();
        Customers customerModel = null;
        Streams streamModel = null;
        Employee employeeModel;

        do {
            if (isLastRevRow(revenueRow)) {
                break;
            }

            String rowLabel = getField(revenueRow, ZERO.getNumber());
            String rowLabelPlusOne = getField(revenueRow + 1, ZERO.getNumber());

            if (rowLabel.equals(THRESHOLD) || rowLabel.equals(THRESHOLD1) || rowLabel.equals(THRESHOLD2) || isRowRevEmpty(revenueRow)) {
                revenueRow++;
                continue;
            }

            if (customerRowLabel.contains(rowLabel)) {
                customerModel = new Customers();
                customerModel.setRowLabels(getField(revenueRow, ZERO.getNumber()));
                customerModel.setReportedHours(getField(revenueRow, ONE.getNumber()));
                customerModel.setRevenue(getField(revenueRow, TWO.getNumber()));
                customerModel.setEffectiveRate(getField(revenueRow, THREE.getNumber()));

            } else if (isStream(rowLabel)) {
                streamModel = new Streams();

                streamModel.setRowLabels(getField(revenueRow, ZERO.getNumber()));
                streamModel.setReportedHours(getField(revenueRow, ONE.getNumber()));
                streamModel.setRevenue(getField(revenueRow, TWO.getNumber()));
                streamModel.setEffectiveRate(getField(revenueRow, THREE.getNumber()));

            } else {

                employeeModel = new Employee();
                employeeModel.setRowLabels(getField(revenueRow, ZERO.getNumber()));
                employeeModel.setReportedHours(getField(revenueRow, ONE.getNumber()));
                employeeModel.setRevenue(getField(revenueRow, TWO.getNumber()));
                employeeModel.setEffectiveRate(getField(revenueRow, THREE.getNumber()));
                employees.add(employeeModel);

                if (isStream(rowLabelPlusOne)) {
                    streamModel.setEmployeesList(new ArrayList<>(employees));
                    streams.add(streamModel);
                    employees.clear();

                } else if (customerRowLabel.contains(rowLabelPlusOne) || rowLabelPlusOne.equals(GRAND_TOTAL)) {
                    streamModel.setEmployeesList(new ArrayList<>(employees));
                    streams.add(streamModel);
                    employees.clear();

                    customerModel.setStreamsList(new ArrayList<>(streams));
                    customers.add(customerModel);
                    streams.clear();
                }
            }
            revenueRow++;
        } while (true);
        return customers;
    }

    public String getField(int rowNumber, Integer cellNumber) {
        Row row = sheet.getRow(rowNumber);
        try {
            return row.getCell(cellNumber).toString();
        } catch (NullPointerException ex) {
            return "";
        }
    }

    public boolean isRowRevEmpty(int rownum) {
        Row row = baseExcel.getSheet(REVENUE_SHEET).getRow(rownum);
        return ExcelFileUtils.isRowEmpty(row);
    }

    public boolean isLastRevRow(int rownum) {
        return isRowRevEmpty(rownum) && isRowRevEmpty(rownum + 1) && isRowRevEmpty(rownum + 2) &&
                isRowRevEmpty(rownum + 3) && isRowRevEmpty(rownum + 4) && isRowRevEmpty(rownum + 5);
    }

}
