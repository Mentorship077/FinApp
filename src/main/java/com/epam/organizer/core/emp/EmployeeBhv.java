package com.epam.organizer.core.emp;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import com.epam.organizer.core.base.BaseExcel;
import com.epam.organizer.core.utils.ExcelFileUtils;
import com.epam.organizer.models.salaryTable.EmpTitle;

import java.util.HashMap;

import static com.epam.organizer.commons.CommonConst.EMPLOYEE_PATH;
import static com.epam.organizer.commons.CommonConst.EMPLOYEE_SHEET;
import static com.epam.organizer.commons.NumberConstant.TWO;
import static com.epam.organizer.commons.NumberConstant.ZERO;


public class EmployeeBhv {

    private BaseExcel baseExcel = new BaseExcel(EMPLOYEE_PATH).openFile();
    private Sheet sheet = baseExcel.getSheet(EMPLOYEE_SHEET);
    private HashMap<String, String> employee = new HashMap<>();
    private HashMap<EmpTitle, String> npList = new HashMap<EmpTitle, String>();


    public HashMap<String, String> getEmployeeTitle() {
        int excelRow = 1;
        do {
            if (isLastEmpRow(excelRow)) {
                break;
            }
            String name = getField(excelRow, ZERO.getNumber());
            String status = getField(excelRow, TWO.getNumber());
            employee.put(name, status);
            excelRow++;
        }
        while (true);
        return employee;
    }

    public HashMap<EmpTitle, String> getBenchList() {
        int excelRow = 1;
        do {
            if (isLastEmpRow(excelRow)) {
                break;
            }
            String name = getField(excelRow, ZERO.getNumber());
            String status = getField(excelRow, TWO.getNumber());

            String np = getField(excelRow, 19);
            if (np.equals("1.0")) {
                npList.put(new EmpTitle(name,status), np);
            }
            excelRow++;
        }
        while (true);
        return npList;
    }


    private String getField(int rowNumber, Integer cellNumber) {
        Row row = sheet.getRow(rowNumber);
        Cell cellValue = row.getCell(cellNumber);
        String string;
        try {
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

                // CELL_TYPE_FORMULA will never happen
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


    public boolean isEmptyEmpRow(int rownum) {
        Row row = sheet.getRow(rownum);
        return ExcelFileUtils.isRowEmpty(row);
    }

    public boolean isLastEmpRow(int rownum) {
        return isEmptyEmpRow(rownum) && isEmptyEmpRow(rownum+1) && isEmptyEmpRow(rownum+2);
    }
}
