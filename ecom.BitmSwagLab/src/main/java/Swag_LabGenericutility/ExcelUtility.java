package Swag_LabGenericutility;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ExcelUtility {
public Object[][] getExcelData(String filePath, String sheetName) {
List<Object[]> rows = new ArrayList<Object[]>();
   try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
             Sheet sheet = workbook.getSheet(sheetName);
               if (sheet == null) {
                throw new RuntimeException("Excel sheet not found: " + sheetName);
            }
       DataFormatter formatter = new DataFormatter();
       int columnCount = sheet.getRow(0).getLastCellNum();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
              Row row = sheet.getRow(i);
              if (row == null) {
                    continue;
                }
         Object[] data = new Object[columnCount];
          for (int j = 0; j < columnCount; j++) {
Cell cell = row.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
 data[j] = cell == null? "": formatter.formatCellValue(cell).trim();
                }

                rows.add(data);
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to read Excel workbook: " + filePath, e);
        }

        return rows.toArray(new Object[rows.size()][]);
    }
}
