package com.epam.organizer.core.graphDataSheet;

import com.epam.organizer.core.base.BaseExcel;
import com.epam.organizer.models.customer.Customers;
import com.epam.organizer.models.customer.Employee;
import com.epam.organizer.models.rm.RMPersonList;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.epam.organizer.commons.CommonConst.*;
import static java.util.stream.Collectors.toList;

public class GraphDataSheet {
    int rowEmpCount;
    private BaseExcel baseExcel = new BaseExcel(REVENUE_PATH).openFile();
    private Sheet sheet = baseExcel.createSheet(RM_SHEETS);
    private int BEGIN_ROW_CREATED_SHEET = 1;


    public void writeSheetGraphs(List<RMPersonList> rmPersonLists) {
        Row row = sheet.createRow(0);
        row.setHeightInPoints(60);
        for (int i = 0; i < RM_HEADER_NAME.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(RM_HEADER_NAME.get(i));
            cell.setCellStyle(getCellSHeaderStyle());
        }
//        RM
        sheet.setColumnWidth(0, 5000);
//            TA Name
        sheet.setColumnWidth(1, 7000);

        for (int i = 0; i < rmPersonLists.size(); i++) {
            int rowCount = BEGIN_ROW_CREATED_SHEET;
            rowEmpCount = BEGIN_ROW_CREATED_SHEET;
            int empSenCountPerRM = 0;

            rowCount++;
            for (int j = 0; j < rmPersonLists.get(i).getEmployees().size(); j++) {
                Row row1 = createCustomRow();

//              RM
                Cell cell1 = row1.createCell(0);
                cell1.setCellValue(rmPersonLists.get(i).getEmployees().get(j).getRm());
                cell1.setCellStyle(getProjectCellStyle());

//              TA Name
                Cell cell2 = row1.createCell(1);
                cell2.setCellValue(rmPersonLists.get(i).getEmployees().get(j).getName());
                cell2.setCellStyle(getStandardCellStyle());

//              Sen Count
                Cell cell3 = row1.createCell(2);
                cell3.setCellValue(rmPersonLists.get(i).getEmployees().get(j).getEmployeeSeniority());
                cell3.setCellStyle(getStandardCellStyle());

                empSenCountPerRM = empSenCountPerRM + rmPersonLists.get(i).getEmployees().get(j).getEmployeeSeniority();
            }


//            Employee count
            Integer EMP_COUNT = rmPersonLists.get(i).getEmployees().size();

//                Seniority per rm
            double average = (double) empSenCountPerRM / EMP_COUNT;

//                Second loop for setting total Emp count and Sen per project for merging cells
            for (int k = 0; k < rmPersonLists.get(i).getEmployees().size(); k++) {
                Row row1 = getRow();

//                Emp Count
                Cell cell15 = row1.createCell(3);
                cell15.setCellStyle(getCountCellStyle());
                cell15.setCellValue(EMP_COUNT);

//                Average Seniority
                Cell cell16 = row1.createCell(4);
                cell16.setCellStyle(getCountCellStyle());
                cell16.setCellValue(new DecimalFormat("##.##").format(average));

//                Revenue

            }

            // Cell merging
            int size = rowCount + (rmPersonLists.get(i).getEmployees().size() - 1);
            if (rowCount != size) {
                sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rowCount + ":A" + size));
                sheet.addMergedRegion(CellRangeAddress.valueOf("D" + rowCount + ":D" + size));
                sheet.addMergedRegion(CellRangeAddress.valueOf("E" + rowCount + ":E" + size));
            }
        }


        baseExcel.saveChangesToFile();
    }

    public Row getRow() {
        return sheet.getRow(rowEmpCount++);
    }

    public Row createCustomRow() {
        return sheet.createRow(BEGIN_ROW_CREATED_SHEET++);
    }

    public CellStyle getCountCellStyle() {
        CellStyle style = baseExcel.createCellStyle();

        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);

        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        return style;
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

    public CellStyle getProjectCellStyle() {
        CellStyle style = baseExcel.createCellStyle();

        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);

        Font font = baseExcel.getWorkbook().createFont();
        font.setBold(true);

        style.setFont(font);
        style.setBorderBottom(BorderStyle.THICK);
        style.setBorderLeft(BorderStyle.THICK);
        style.setBorderRight(BorderStyle.THICK);
        style.setBorderTop(BorderStyle.THICK);
        return style;
    }
}
