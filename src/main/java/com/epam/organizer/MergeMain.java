package com.epam.organizer;

import com.epam.organizer.core.managersSheet.MargeFunctionality;

import static com.epam.organizer.commons.CommonConst.OUTPUT_DIRECTORY;
import static com.epam.organizer.commons.CommonConst.REVENUE_PATH;
import static com.epam.organizer.core.utils.Utils.getExcelPath;

public class MergeMain {
    public static void main(String[] args) {
        REVENUE_PATH = OUTPUT_DIRECTORY + "\\" + getExcelPath();
        new MargeFunctionality().mergeProjectsCells();
    }

}
