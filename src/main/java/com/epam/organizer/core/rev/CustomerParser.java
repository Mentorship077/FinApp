package com.epam.organizer.core.rev;

import com.epam.organizer.core.base.BaseExcel;
import com.epam.organizer.core.utils.ExcelFileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

import static com.epam.organizer.commons.CommonConst.*;
import static com.epam.organizer.commons.NumberConstant.ZERO;
import static com.epam.organizer.core.utils.Utils.getFirstPartOfStream;
import static com.epam.organizer.core.utils.Utils.isStream;
import static org.apache.poi.ddf.EscherColorRef.SysIndexProcedure.THRESHOLD;

public class CustomerParser {
    private static int revenueRow = 10;
    private BaseExcel baseExcel = new BaseExcel(REVENUE_PATH).openFile();
    private Sheet sheet = baseExcel.getSheet(REVENUE_SHEET);


    public List<String> getCustomerRowLabel() {
        List<String> customersRowLabel = new ArrayList<>();

        String streamName2 = "";
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
            if (isStream(rowLabelPlusOne)) {
                String streamName1 = getFirstPartOfStream(rowLabelPlusOne);
                if (!streamName1.equals(streamName2)) {
                    customersRowLabel.add(rowLabel);
                }
                streamName2 = getFirstPartOfStream(rowLabelPlusOne);
            }
            revenueRow++;
        } while (true);
        baseExcel.saveChangesToFile();
        return customersRowLabel;
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
        return isRowRevEmpty(rownum) & isRowRevEmpty(rownum + 1) & isRowRevEmpty(rownum + 2) & isRowRevEmpty(rownum + 3);
    }

}
