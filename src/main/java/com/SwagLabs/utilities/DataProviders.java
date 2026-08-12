package com.SwagLabs.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class DataProviders {

    private static final String FILE_PATH =
            System.getProperty("user.dir")
            + "/src/test/resources/testdata/TestData.xlsx";

    public static Object[][] getSheetData(String sheetName) throws IOException {
        List<Object[]> data = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException(
                        "Sheet '" + sheetName + "' does not exist.");
            }
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null)
                    continue;
                Object[] rowData = new Object[row.getLastCellNum()];
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    rowData[j] = getCellValue(cell);
                }
                data.add(rowData);
            }
        }
        return data.toArray(new Object[0][]);
    }

    private static Object getCellValue(Cell cell) {
        if (cell == null)
            return "";
        switch (cell.getCellType()) {
        case STRING:
            return cell.getStringCellValue();
        case NUMERIC:
            if (DateUtil.isCellDateFormatted(cell))
                return cell.getDateCellValue();
            return String.valueOf((int) cell.getNumericCellValue());
        case BOOLEAN:
            return cell.getBooleanCellValue();
        default:
            return "";
        }
    }

    // ── FIXED: calls your own getSheetData(), no separate utility needed ──
    @DataProvider(name = "checkoutData")
    public static Object[][] checkoutData() throws IOException {
        return getSheetData("checkOutDetails");
    }
}