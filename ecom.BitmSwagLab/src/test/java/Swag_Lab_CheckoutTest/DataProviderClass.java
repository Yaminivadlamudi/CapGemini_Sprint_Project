
package Swag_Lab_CheckoutTest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;

public class DataProviderClass {

    private static Object[][] getTestData(String requiredResult) {

        List<Object[]> data = new ArrayList<>();

        try (InputStream input = DataProviderClass.class
                .getClassLoader()
                .getResourceAsStream("SwagLabs_SingleSheet_TestData.xlsx")) {

            if (input == null) {
                throw new RuntimeException(
                        "Excel file not found in src/test/resources"
                );
            }

            try (Workbook workbook = WorkbookFactory.create(input)) {

                Sheet sheet = workbook.getSheet("TestData");

                if (sheet == null) {
                    throw new RuntimeException(
                            "TestData sheet not found in Excel"
                    );
                }

                DataFormatter formatter = new DataFormatter();

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                    Row row = sheet.getRow(i);

                    if (row == null) {
                        continue;
                    }

                    String username =
                            formatter.formatCellValue(row.getCell(0)).trim();

                    String password =
                            formatter.formatCellValue(row.getCell(1)).trim();

                    String firstName =
                            formatter.formatCellValue(row.getCell(2)).trim();

                    String lastName =
                            formatter.formatCellValue(row.getCell(3)).trim();

                    String postalCode =
                            formatter.formatCellValue(row.getCell(4)).trim();

                    String scenario =
                            formatter.formatCellValue(row.getCell(5)).trim();

                    String expectedResult =
                            formatter.formatCellValue(row.getCell(6)).trim();

                    if (expectedResult.equalsIgnoreCase(requiredResult)) {

                        data.add(new Object[] {
                                username,
                                password,
                                firstName,
                                lastName,
                                postalCode,
                                scenario,
                                expectedResult
                        });
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to read checkout test data from Excel", e
            );
        }

        if (data.isEmpty()) {
            throw new RuntimeException(
                    "No Excel rows found for ExpectedResult: " + requiredResult
            );
        }

        return data.toArray(new Object[0][]);
    }

    @DataProvider(name = "positiveCheckoutData")
    public static Object[][] positiveCheckoutData() {
        return getTestData("SUCCESS");
    }

    @DataProvider(name = "negativeCheckoutData")
    public static Object[][] negativeCheckoutData() {
        return getTestData("CHECKOUT_ERROR");
    }
}
