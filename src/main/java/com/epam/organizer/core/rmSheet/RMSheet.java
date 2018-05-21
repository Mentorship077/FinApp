package com.epam.organizer.core.rmSheet;

import com.epam.organizer.core.base.BaseExcel;
import com.epam.organizer.models.Customers;
import com.epam.organizer.models.Employee;
import com.epam.organizer.models.rm.RMPersonList;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.List;

import static com.epam.organizer.commons.CommonConst.*;
import static java.util.stream.Collectors.toList;

public class RMSheet {
    private BaseExcel baseExcel = new BaseExcel(REVENUE_PATH).openFile();
    private Sheet sheet = baseExcel.createSheet(RM_SHEETS);
    private int BEGIN_ROW_CREATED_SHEET = 1;

    private static List<String> getDistinctRM(List<Customers> customers) {
        List<String> allRMs = new ArrayList<>();
        customers.forEach(customer -> customer.getStreamsList()
                .forEach(stream -> stream.getEmployeesList()
                        .forEach(employee ->
                                allRMs.add(employee.getRm()))));

        return allRMs.stream().distinct().collect(toList());
    }

    public static void main(String[] args) {
        RMSheet rmSheet = new RMSheet();
//        rmSheet.writeSheetRMs();


    }

    private static List<Employee> getAllEmpForRM(List<Employee> employees, String id) {
        return employees
                .stream()
                .filter(x -> x.getRm().equalsIgnoreCase(id))
                .collect(toList());
    }

    public List<RMPersonList> processAllEmployees(List<Customers> customers) {
        List<String> rms = getDistinctRM(customers);
        List<RMPersonList> rmsEmpList = new ArrayList<>();

        rms.forEach(rm -> {
            List<Employee> empList = new ArrayList<>();
            customers.forEach(customer -> customer.getStreamsList()
                    .forEach(stream -> {

                        List<Employee> list = getAllEmpForRM(stream.getEmployeesList(), rm);
                        empList.addAll(list);
                    }));


            RMPersonList model = new RMPersonList(rm, empList);
            rmsEmpList.add(model);
        });
        return rmsEmpList;
    }

    public void writeSheetRMs(List<RMPersonList> rmPersonLists) {
        Row row = sheet.createRow(0);
        row.setHeightInPoints(60);
        for (int i = 0; i < RM_HEADER_NAME.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(RM_HEADER_NAME.get(i));
            cell.setCellStyle(getCellSHeaderStyle());
        }
//        RM
        sheet.setColumnWidth(0, 3300);
//            TA Name
        sheet.setColumnWidth(1, 5000);

        for (int i = 0; i < rmPersonLists.size(); i++) {
            int rowCount = BEGIN_ROW_CREATED_SHEET;

            for (int j = 0; j < rmPersonLists.get(i).getEmployees().size(); j++) {
                Row row1 = createCustomRow();

//              RM
                Cell cell1 = row1.createCell(0);
                cell1.setCellValue(rmPersonLists.get(i).getEmployees().get(j).getRm());
                cell1.setCellStyle(getStandardCellStyle());

//              TA Name
                Cell cell2 = row1.createCell(1);
                cell2.setCellValue(rmPersonLists.get(i).getEmployees().get(j).getName());
                cell2.setCellStyle(getStandardCellStyle());

//              Sen Count
                Cell cell3 = row1.createCell(2);
                cell3.setCellValue(rmPersonLists.get(i).getEmployees().get(j).getEmployeeCount());
                cell3.setCellStyle(getStandardCellStyle());
            }


                int size = rowCount + (rmPersonLists.get(i).getEmployees().size() - 1);
                if (rowCount != size) {
                    sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rowCount + ":A" + size));
                }
        }

        baseExcel.saveChangesToFile();
    }

    public Row createCustomRow() {
        return sheet.createRow(BEGIN_ROW_CREATED_SHEET++);
    }

    public CellStyle getStandardCellStyle() {

        CellStyle style = baseExcel.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);

        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.LEFT);
        return style;
    }

    private CellStyle getCellSHeaderStyle() {
        CellStyle style = baseExcel.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        style.setWrapText(true);

        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        return style;
    }
}
