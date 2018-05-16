package com.epam.organizer.core.managersSheet;

import com.epam.organizer.core.base.BaseExcel;
import com.epam.organizer.models.Customers;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

import static com.epam.organizer.commons.CommonConst.MANAGERS_SHEET_NAME;
import static com.epam.organizer.commons.CommonConst.REVENUE_PATH;

public class ProjectPMFunctionality {
    private BaseExcel baseExcel = new BaseExcel(REVENUE_PATH).openFile();
    private Sheet sheetGet = baseExcel.getSheet(MANAGERS_SHEET_NAME);
    private int BEGIN_ROW_CREATED_SHEET = 0;

    public void setPM(List<Customers> customers) {

        for (int i = 0; i < customers.size(); i++) {
            Customers customer = customers.get(i);

            for (int j = 0; j < customer.getStreamsList().size(); j++) {
                Cell cell17 = null;
                Cell cell18 = null;
                double sumRevenuePerStream = 0;
                double sumCostPerStream = 0;
                double sumRevenue152perStream = 0;
                double sumLostRevenue = 0;

                int empSize = customer.getStreamsList().get(j).getEmployeesList().size();
                String string = null;
                for (int k = 0; k < empSize; k++) {
                   getRow();
//                    Revenue
                    double revenue = Double.parseDouble(customer.getStreamsList().get(j).getEmployeesList().get(k).getRevenue());
                    sumRevenuePerStream = sumRevenuePerStream + revenue;

//                   Revenue based on 152
                    String cell = getField(BEGIN_ROW_CREATED_SHEET, 6);
                    double rev152 = Double.parseDouble(cell);
                    sumRevenue152perStream = sumRevenue152perStream + rev152;

                    String cell7 = getField(BEGIN_ROW_CREATED_SHEET, 7);
                    if (cell7.equals("rate mismatch")) {
                    } else {
                        double lost = Double.parseDouble(cell);
                        sumLostRevenue = sumLostRevenue + lost;
                    }


//                    cost
                    String cell1 = getField(BEGIN_ROW_CREATED_SHEET, 11);
                    double cost = Double.parseDouble(cell1);
                    sumCostPerStream = sumCostPerStream + cost;

                    string = getField(BEGIN_ROW_CREATED_SHEET, 1);

//                    if ((k+1) == (customer.getStreamsList().get(j).getEmployeesList().size())) {
//
//                    }
                }
//                    Customer PM
                Row row1 = sheetGet.getRow(BEGIN_ROW_CREATED_SHEET);

                cell17 = row1.createCell(17);
                cell17.setCellStyle(getStandardCellStyle());
//                    Customer PM 152
                cell18 = row1.createCell(18);
                cell18.setCellStyle(getStandardCellStyle());

                double result = ((sumRevenuePerStream - sumCostPerStream) / sumRevenuePerStream) * 100;
                cell17.setCellValue(result);

                double result152 = ((sumRevenue152perStream - sumCostPerStream) / sumRevenue152perStream) * 100;
                cell18.setCellValue(result152);

                String s = customer.getStreamsList().get(j).getRowLabels();
//                System.out.println(s);
//                System.out.println(string);
            }
        }

        baseExcel.saveChangesToFile();
    }

    public String getField(int rowNumber, Integer cellNumber) {
        Row row = baseExcel.getSheet(MANAGERS_SHEET_NAME).getRow(rowNumber);
        String string;
        try {
            Cell cellValue = row.getCell(cellNumber);
            switch (cellValue.getCellTypeEnum()) {
                case BOOLEAN:
                    string = String.valueOf(cellValue.getBooleanCellValue());
                    break;
                case NUMERIC:
                    string = String.valueOf(cellValue.getNumericCellValue());
                    break;
                case STRING:
                    string = cellValue.getStringCellValue();
                    break;
                case BLANK:
                    string = "Empty Cell";
                    break;
                case ERROR:
                    string = String.valueOf(cellValue.getErrorCellValue());
                    break;

                case FORMULA:
                    baseExcel.evaluateAllFormulaCells().evaluateInCell(cellValue);
                    string = String.valueOf(cellValue.getNumericCellValue());
                    break;
                default:
                    string = "Wow CELL have unrecognized type";
                    break;
            }
        } catch (NullPointerException e) {
            return String.valueOf(0);
        }

        return string;
    }


    public Row getRow() {
        return sheetGet.getRow(BEGIN_ROW_CREATED_SHEET++);
    }

    //    Styles

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

    private CellStyle getCellSTotalStyle() {
        CellStyle style = baseExcel.createCellStyle();
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

    public CellStyle getStandardCellStyle() {
//        if (TITLE.equals(DOESN_T_FOUND_EMPLOYEE)) {
//            return getRedCellStyle();
//        } else if (RATE.equals(RATE_ZERO)) {
//            return getYellowCellStyle();
//        }

        CellStyle style = baseExcel.createCellStyle();

        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.LEFT);
        return style;
    }

    public CellStyle getRedCellStyle() {
        CellStyle style = baseExcel.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.RED.getIndex());

        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        return style;
    }

    public CellStyle getYellowCellStyle() {
        CellStyle style = baseExcel.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());

        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        return style;
    }
}

