
package Swag_LabFileutility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FileUtility {

    public String readDataFromPropertyFile(String key) {

        Properties properties = new Properties();

        try (FileInputStream fis =
                new FileInputStream("src/main/resources/commondata.properties")) {

            properties.load(fis);

            String value = properties.getProperty(key);

            if (value == null) {
                throw new RuntimeException(
                        "Property not found in commondat.properties: " + key);
            }

            return value.trim();

        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to read commondat.properties", e);
        }
    }
}
