package pt.ipp.isep.dei.project.model.device.devicetypes;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * WaterHeater tests class.
 */

public class WaterHeaterTest {

    @Test
    public void getTypeTest() {
        WaterHeater waterHeater = new WaterHeater(200.0, 25.0, 10.0);
        DeviceType expectedResult = DeviceType.WATER_HEATER;
        DeviceType result = waterHeater.getType();
        assertEquals(expectedResult, result);
    }

    //getConsumption Tests

    @Test
    public void getConsumptionTest() {
        WaterHeater waterHeater = new WaterHeater(200.0, 25.0, 0.9);
        Double coldT = 12.0;
        Double waterV = 300.0;
        Double hotT = 25.0;
        waterHeater.setAttributeValue("hotWaterTemperature", hotT);
        waterHeater.setAttributeValue("coldWaterTemperature", coldT);
        waterHeater.setAttributeValue("volumeOfWaterToHeat", waterV);
        Double expectedResult = 4082.13;
        Double result = waterHeater.getConsumption();
        assertEquals(expectedResult, result);
    }
    @Test
    public void getConsumptionTestNull() {
        WaterHeater waterHeater = new WaterHeater(200.0, 25.0, 0.9);
        Double coldT = null;
        Double waterV = 300.0;
        Double hotT = null;
        waterHeater.setAttributeValue("hotWaterTemperature", hotT);
        waterHeater.setAttributeValue("coldWaterTemperature", coldT);
        waterHeater.setAttributeValue("volumeOfWaterToHeat", waterV);
        Double expectedResult = 7850.25;
        Double result = waterHeater.getConsumption();
        assertEquals(expectedResult, result);
    }

    @Test
    public void getConsumptionWithSetsFailsTest() {
        WaterHeater waterHeater = new WaterHeater(200.0, 25.0, 0.9);
        Double coldT = 30.0;
        Double waterV = 200.0;
        Double hotT = 25.0;
        waterHeater.setAttributeValue("coldWaterTemperature", coldT);
        waterHeater.setAttributeValue("hotWaterTemperature",hotT);
        waterHeater.setAttributeValue("volumeOfWaterToHeat", waterV);
        Double expectedResult = 0.0;
        Double result = waterHeater.getConsumption();
        assertEquals(expectedResult, result);
    }

    @Test
    public void getConsumptionTestFails() {
        WaterHeater waterHeater = new WaterHeater(200.0, 25.0, 0.9);
        Double coldT = 200.0;
        Double waterV = 300.0;
        waterHeater.setAttributeValue("coldWaterTemperature", coldT);
        waterHeater.setAttributeValue("volumeOfWaterToHeat", waterV);
        double expectedResult = 0;
        double result = waterHeater.getConsumption();
        assertEquals(expectedResult, result);
    }

    @Test
    public void getConsumptionTestColdWaterEqualsHotWater() {
        WaterHeater waterHeater = new WaterHeater(200.0, 25.0, 0.9);
        Double coldT = 25.0;
        Double waterV = 100.0;
        Double hotT = 25.0;
        waterHeater.setAttributeValue("coldWaterTemperature", coldT);
        waterHeater.setAttributeValue("volumeOfWaterToHeat", waterV);
        waterHeater.setAttributeValue("hotWaterTemperature", hotT);
        double expectedResult = 0;
        double result = waterHeater.getConsumption();
        assertEquals(expectedResult, result);
    }

    @Test
    public void getConsumptionTestColdWaterMinorHotWater() {
        WaterHeater waterHeater = new WaterHeater(200.0, 25.0, 0.9);
        Double coldT = 2.0;
        Double waterV = 100.0;
        Double hotT = 25.0;
        waterHeater.setAttributeValue("coldWaterTemperature", coldT);
        waterHeater.setAttributeValue("volumeOfWaterToHeat", waterV);
        waterHeater.setAttributeValue("hotWaterTemperature", hotT);
        double expectedResult = 2407.41;
        double result = waterHeater.getConsumption();
        assertEquals(expectedResult, result);
    }

    @Test
    public void getConsumptionTestColdWaterMinorHotWater2() {
        WaterHeater waterHeater = new WaterHeater(200.0, 25.0, 0.9);
        Double coldT = 30.0;
        Double waterV = 800.0;
        Double hotT = 25.0;
        waterHeater.setAttributeValue("coldWaterTemperature", coldT);
        waterHeater.setAttributeValue("volumeOfWaterToHeat", waterV);
        waterHeater.setAttributeValue("hotWaterTemperature", hotT);
        double expectedResult = 0.0;
        double result = waterHeater.getConsumption();
        assertEquals(expectedResult, result);
    }
    @Test
    public void getConsumptionTestColdWaterMinorHotWater3() {
        WaterHeater waterHeater = new WaterHeater(200.0, 25.0, 0.9);
        Double coldT = 25.0;
        Double waterV = 800.0;
        Double hotT = 25.0;
        waterHeater.setAttributeValue("coldWaterTemperature", coldT);
        waterHeater.setAttributeValue("volumeOfWaterToHeat", waterV);
        waterHeater.setAttributeValue("hotWaterTemperature", hotT);
        double expectedResult = 0.0;
        double result = waterHeater.getConsumption();
        assertEquals(expectedResult, result);
    }


    //Test Attributes

    @Test
    void seeIfSetVolumeWaterWorks() {
        WaterHeater waterHeater = new WaterHeater();
        Double volumeOfWater = 12.0;
        waterHeater.setVolumeOfWater(volumeOfWater);
        Double result = waterHeater.getVolumeWater();
        Double expectedResult = 12.0;
        assertEquals(expectedResult, result);
    }


    @Test
    public void seeIfGetAndSetAttributeValue() {
        WaterHeater waterHeater = new WaterHeater();
        Double volumeOfWater = 0.6;
        String attribute = "volumeOfWater";
        Double expectedResult = 0.6;
        boolean setResult = waterHeater.setAttributeValue(attribute, volumeOfWater);
        Object getResult = waterHeater.getAttributeValue(attribute);
        assertEquals(expectedResult, getResult);
        assertTrue(setResult);
    }


    @Test
    public void seeIfSetAttributeValueInvalid() {
        WaterHeater waterHeater = new WaterHeater();
        Double value = 0.6;
        String attribute = "invalid";
        boolean result = waterHeater.setAttributeValue(attribute, value);
        assertFalse(result);
    }

    @Test
    public void seeIfGetAttributeNames() {
        WaterHeater waterHeater = new WaterHeater();
        List<String> result = waterHeater.getAttributeNames();
        assertTrue(result.contains("volumeOfWater"));
        assertTrue(result.contains("hotWaterTemperature"));
        assertTrue(result.contains("coldWaterTemperature"));
        assertTrue(result.contains("performanceRatio"));
        assertTrue(result.contains("volumeOfWaterToHeat"));
        assertEquals(result.size(), 5);
    }

    @Test
    public void seeIfGetAttributeValueDefaultTest() {
        WaterHeater waterHeater = new WaterHeater();
        String attribute = "Lisboa";
        Double expectedResult = 0.0;
        Object getResult = waterHeater.getAttributeValue(attribute);
        assertEquals(expectedResult, getResult);
    }

    @Test
    public void seeIfGetAndSetAttributeValues() {
        WaterHeater waterHeater = new WaterHeater();
        String attribute = "volumeOfWater";
        Double expectedResult = 2.0;
        Double attributeValue = 2.0;
        boolean setResult = waterHeater.setAttributeValue(attribute, attributeValue);
        Object getResult = waterHeater.getAttributeValue(attribute);
        assertEquals(expectedResult, getResult);
        assertTrue(setResult);

        attribute = "hotWaterTemperature";
        attributeValue = 3.0;
        expectedResult = 3.0;
        setResult = waterHeater.setAttributeValue(attribute, attributeValue);
        getResult = waterHeater.getAttributeValue(attribute);
        assertEquals(expectedResult, getResult);
        assertTrue(setResult);

        attribute = "coldWaterTemperature";
        attributeValue= 4.0;
        expectedResult = 4.0;
        setResult = waterHeater.setAttributeValue(attribute, attributeValue);
        getResult = waterHeater.getAttributeValue(attribute);
        assertEquals(expectedResult, getResult);
        assertTrue(setResult);

        attribute = "performanceRatio";
        expectedResult = 5.0;
        attributeValue = 5.0;
        setResult = waterHeater.setAttributeValue(attribute, attributeValue);
        getResult = waterHeater.getAttributeValue(attribute);
        assertEquals(expectedResult, getResult);
        assertTrue(setResult);

        attribute = "volumeOfWaterToHeat";
        expectedResult = 10.0;
        attributeValue = 10.0;
        setResult = waterHeater.setAttributeValue(attribute, attributeValue);
        getResult = waterHeater.getAttributeValue(attribute);
        assertEquals(expectedResult, getResult);
        assertTrue(setResult);
    }

    @Test
    public void seeIFSetAttributeValuesFails() {
        WaterHeater waterHeater = new WaterHeater();
        String attribute = "volumeOfWater";
        int attributeValue = 2;
        boolean setResult = waterHeater.setAttributeValue(attribute, attributeValue);
        assertFalse(setResult);

        attribute = "hotWaterTemperature";
        attributeValue = 3;
        setResult = waterHeater.setAttributeValue(attribute, attributeValue);
        assertFalse(setResult);

        attribute = "coldWaterTemperature";
        attributeValue = 4;
        setResult = waterHeater.setAttributeValue(attribute, attributeValue);
        assertFalse(setResult);

        attribute = "performanceRatio";
        attributeValue = 5;
        setResult = waterHeater.setAttributeValue(attribute, attributeValue);
        assertFalse(setResult);

        attribute = "volumeOfWaterToHeat";
        attributeValue = 10;
        setResult = waterHeater.setAttributeValue(attribute, attributeValue);
        assertFalse(setResult);
    }
    @Test
    public void seeIFSetAttributeValuesFails2() {
        WaterHeater waterHeater = new WaterHeater();
        String attribute = "njfdjkndfk";
        int attributeValue = 2;
        boolean setResult = waterHeater.setAttributeValue(attribute, attributeValue);
        assertFalse(setResult);

        attribute = "htfcf";
        attributeValue = 3;
        setResult = waterHeater.setAttributeValue(attribute, attributeValue);
        assertFalse(setResult);

        attribute = "fhj";
        attributeValue = 4;
        setResult = waterHeater.setAttributeValue(attribute, attributeValue);
        assertFalse(setResult);

        attribute = "fhjg";
        attributeValue = 5;
        setResult = waterHeater.setAttributeValue(attribute, attributeValue);
        assertFalse(setResult);

        attribute = "gfdjcktuyvuh";
        attributeValue = 10;
        setResult = waterHeater.setAttributeValue(attribute, attributeValue);
        assertFalse(setResult);
    }
    @Test
    public void seeIfGetAndSetAttributeValues2() {
        WaterHeater waterHeater = new WaterHeater();
        String attribute = "sgddhfg";
        Double expectedResult = 0.0;
        Double attributeValue = 3.0;
        boolean setResult = waterHeater.setAttributeValue(attribute, attributeValue);
        Object getResult = waterHeater.getAttributeValue(attribute);
        assertEquals(expectedResult, getResult);
        assertFalse(setResult);

        attribute = "dghetrft";
        attributeValue = 3.0;
        expectedResult = 0.0;
        setResult = waterHeater.setAttributeValue(attribute, attributeValue);
        getResult = waterHeater.getAttributeValue(attribute);
        assertEquals(expectedResult, getResult);
        assertFalse(setResult);

        attribute = "fhjhgj";
        attributeValue= 2.0;
        expectedResult = 0.0;
        setResult = waterHeater.setAttributeValue(attribute, attributeValue);
        getResult = waterHeater.getAttributeValue(attribute);
        assertEquals(expectedResult, getResult);
        assertFalse(setResult);

        attribute = "fcgh";
        expectedResult = 0.0;
        attributeValue = 5.0;
        setResult = waterHeater.setAttributeValue(attribute, attributeValue);
        getResult = waterHeater.getAttributeValue(attribute);
        assertEquals(expectedResult, getResult);
        assertFalse(setResult);

        attribute = "dfghj";
        expectedResult = 0.0;
        attributeValue = 5.0;
        setResult = waterHeater.setAttributeValue(attribute, attributeValue);
        getResult = waterHeater.getAttributeValue(attribute);
        assertEquals(expectedResult, getResult);
        assertFalse(setResult);
    }
}
