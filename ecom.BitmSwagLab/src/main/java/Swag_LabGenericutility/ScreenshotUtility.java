
package Swag_LabGenericutility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtility {

    public static String capture(WebDriver driver, String testName) {

        try {
            Path folder = Paths.get("screenshots");
            Files.createDirectories(folder);
            String timestamp = new SimpleDateFormat(
                    "yyyyMMdd_HHmmss").format(new Date());
             String safeName = testName.replaceAll(
                    "[^a-zA-Z0-9_-]", "_");
            Path destination = folder.resolve(safeName + "_" + timestamp + ".png");
File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
Files.copy(source.toPath(), destination);
return destination.toAbsolutePath().toString();

        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to save screenshot", e);
        }
    }
}
