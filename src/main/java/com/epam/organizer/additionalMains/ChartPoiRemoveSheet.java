package com.epam.organizer.additionalMains;

import com.epam.organizer.core.rev.RevenueParser;

import static com.epam.organizer.commons.CommonConst.OUTPUT_DIRECTORY;
import static com.epam.organizer.commons.CommonConst.REVENUE_PATH;
import static com.epam.organizer.core.utils.Utils.getExcelPath;

public class ChartPoiRemoveSheet {
    public static void main(String[] args) {
        REVENUE_PATH = OUTPUT_DIRECTORY + "\\" + getExcelPath();
        RevenueParser parser = new RevenueParser();
        parser.deleteEvaluationWarning();
    }
}
