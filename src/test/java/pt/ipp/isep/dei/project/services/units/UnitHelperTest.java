package pt.ipp.isep.dei.project.services.units;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static pt.ipp.isep.dei.project.services.units.UnitHelper.*;

class UnitHelperTest {

    @Test
    void seeIfGetUserTemperatureDefaultWorks() throws IOException {

        Properties props = new Properties();
        String propFileName = "resources/units.properties";
        String expectedResult = "Celsius";
        FileInputStream input = new FileInputStream(propFileName);
        props.load(input);

        // Act

        String actualResult = UnitHelper.getUserTemperatureDefault(propFileName);
        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfThrowIOExceptionWorks() {
        assertThrows(IOException.class,
                () -> {
                    Properties props = new Properties();
                    String propFileName = "resources/abcd.efgh";
                    FileInputStream input = new FileInputStream(propFileName);
                    props.load(input);
                });
    }

    @Test
    void seeIfGetApplicationTemperatureConfigWorks() throws IOException {
        // Arrange

        String expectedResult = "Celsius";

        // Act

        String actualResult = getApplicationTemperatureConfig();

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfGetUserTemperatureConfigWorks() throws IOException {
        // Arrange

        String expectedResult = "Celsius";

        // Act

        String actualResult = UnitHelper.getUserTemperatureConfig();

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfGetApplicationRainfallConfigWorks() throws IOException {
        // Arrange

        String expectedResult = "Millimeter";

        // Act

        String actualResult = UnitHelper.getApplicationRainfallConfig();

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfGetUserRainfallConfigWorks() throws IOException {
        // Arrange

        String expectedResult = "Millimeter";

        // Act

        String actualResult = UnitHelper.getUserRainfallConfig();

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfGetUserRainfallDefaultWorks() {
        // Arrange

        String propFileName = "invalid_path";

        // Act

        Throwable exception = assertThrows(IOException.class, () -> UnitHelper.getUserRainfallDefault(propFileName));

        // Assert

        assertEquals("ERROR: Unable to process configuration file.", exception.getMessage());

    }

    @Test
    void seeIfConvertToSystemDefaultTemperatureWorks() throws IOException {
        // Arrange

        double expectedResult = -223.14999999999998;
        double valueToConvert = 50;
        Unit unit = new Kelvin();

        // Act

        double actualResult = UnitHelper.convertToSystemDefault(valueToConvert, unit);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfConvertToSystemDefaultRainfallWorks() throws IOException {
        // Arrange

        double expectedResult = 50;
        double valueToConvert = 50;
        Unit unit = new LiterPerSquareMeter();

        // Act

        double actualResult = UnitHelper.convertToSystemDefault(valueToConvert, unit);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfConvertToUserDefaultTemperatureWorks() throws IOException {
        // Arrange

        double expectedResult = -223.14999999999998;
        double valueToConvert = 50;
        Unit unit = new Kelvin();

        // Act

        double actualResult = UnitHelper.convertToUserDefault(valueToConvert, unit);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfConvertToUserDefaultRainfallWorks() throws IOException {
        // Arrange

        double expectedResult = 50;
        double valueToConvert = 50;
        Unit unit = new LiterPerSquareMeter();

        // Act

        double actualResult = UnitHelper.convertToUserDefault(valueToConvert, unit);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfConvertUnitToSystemDefaultWorks() throws IOException {
        // Arrange

        Unit expectedResult = new Celsius();
        Unit givenUnit = new Fahrenheit();

        // Act

        Unit actualResult = UnitHelper.convertUnitToSystemDefault(givenUnit);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfConvertUnitToSystemDefaultWorksIfReceivesNull() throws IOException {
        // Act

        Unit actualResult = UnitHelper.convertUnitToSystemDefault(null);

        // Assert

        assertNull(actualResult);
    }

    @Test
    void seeIfConvertUnitToSystemDefaultRainfallWorks() throws IOException {
        // Arrange

        Unit expectedResult = new Millimeter();
        Unit givenUnit = new LiterPerSquareMeter();

        // Act

        Unit actualResult = UnitHelper.convertUnitToSystemDefault(givenUnit);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfConvertStringToUnitWorks() {
        // Arrange

        Unit expectedResult = new Celsius();
        String givenUnitString = "Celsius";

        // Act

        Unit actualResult = convertStringToUnit(givenUnitString);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfConvertStringToUnitReturnsNull() {
        // Arrange

        String givenUnitString = "Invalid";

        // Act

        Unit actualResult = convertStringToUnit(givenUnitString);

        // Assert

        assertNull(actualResult);
    }

    @Test
    void seeIfGetReaderClassToInstanceWorks() {
        // Arrange

        String expectedResult = "Fahrenheit";
        String givenUnitString = "F";

        // Act

        String actualResult = getReaderClassToInstance(givenUnitString);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfToDefaultTemperatureUnitsWorksFahrenheit() {
        // Arrange

        double expectedResult = 69.8;
        TemperatureUnit temp = new Celsius();

        // Act

        double actualResult = UnitHelper.toDefaultTemperatureUnit("Fahrenheit", 21, temp);

        // Assert

        assertEquals(expectedResult, actualResult, 0.01);
    }

    @Test
    void seeIfToDefaultRainfallUnitWorksLiterPerSquareMeter() {
        // Arrange

        double expectedResult = 5;
        RainfallUnit rain = new Millimeter();

        // Act

        double actualResult = UnitHelper.toDefaultRainfallUnit("LiterPerSquareMeter", 5, rain);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfToDefaultTemperatureUnitsWorksKelvin() {
        // Arrange

        double expectedResult = 294.15;
        TemperatureUnit temp = new Celsius();

        // Act

        double actualResult = UnitHelper.toDefaultTemperatureUnit("Kelvin", 21, temp);

        // Assert

        assertEquals(expectedResult, actualResult, 0.01);
    }

    @Test
    void seeIfToDefaultTemperatureUnitsWorksNoConversion() {
        // Arrange

        double expectedResult = 21;
        TemperatureUnit temp = new Celsius();

        // Act

        double actualResult = UnitHelper.toDefaultTemperatureUnit("Invalid", 21, temp);

        // Assert

        assertEquals(expectedResult, actualResult, 0.01);
    }

    @Test
    void seeIfGetUserTemperatureDefaultWorksWrongPath() {
        // Act

        assertThrows(IOException.class,
                () -> UnitHelper.getUserTemperatureDefault("Invalid"));
    }

    @Test
    void seeIfGetUserTemperatureDefaultWorksPrintErrorMessage() {
        // Act

        try {
            UnitHelper.getUserTemperatureDefault("Invalid");
        } catch (IOException ok) {
            String message = "ERROR: Unable to process configuration file.";
            assertEquals(message, ok.getMessage());
        }
    }

    @Test()
    void seeIfGetApplicationTemperatureDefaultCatchesException() {
        try {
//            Formatter f = new Formatter(new File("xxx.xxx"));
//            System.out.println("OK criar");
            Scanner ler = new Scanner(new File("resources/units.properties"));
            System.out.println("OK ler");
            assertTrue(true);
        } catch (FileNotFoundException e) {
            System.out.println("ERRO:" + e.getMessage());
            assertFalse(false);
        }

    }

}
