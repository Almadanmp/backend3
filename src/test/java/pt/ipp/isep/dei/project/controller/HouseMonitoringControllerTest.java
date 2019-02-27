package pt.ipp.isep.dei.project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.project.model.*;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * House Monitoring - controller Tests
 */

class HouseMonitoringControllerTest {

    // Common artifacts for testing in this class.

    private HouseMonitoringController controller = new HouseMonitoringController();
    private GeographicArea validHouseArea;
    private House validHouse;
    private Room validRoom;
    private Sensor validTemperatureSensor; // Is a temperature sensor with valid readings.
    private SensorList validSensorList; // Contains the mock sensors mentioned above.
    private SimpleDateFormat validSdf; // SimpleDateFormat dd/MM/yyyy HH:mm:ss
    private Date validDate1;
    private Date validDate2;
    private Date validDate3;
    private Date validDate4;
    private Date validDate5;
    private Date validDate6;

    @BeforeEach
    void arrangeArtifacts() {
        // Sets Up Geographic Area, House, Room and Lists.

        validHouseArea = new GeographicArea("Portugal", new TypeArea("Country"), 300,
                200, new Local(45, 30, 30));
        validHouse = new House("ISEP", new Address("Rua Dr. António Bernardino de Almeida",
                "4455-125", "Porto"),
                new Local(20, 20, 20), new GeographicArea("Porto", new TypeArea("Cidade"),
                2, 3, new Local(4, 4, 100)), 60,
                180, new ArrayList<>());
        validHouse.setMotherArea(validHouseArea);
        validRoom = new Room("Bedroom", 2, 15, 15, 10);
        RoomList validRoomList = new RoomList();
        validRoomList.add(validRoom);
        validSensorList = new SensorList();
        validRoom.setSensorList(validSensorList);
        validSdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            validDate1 = validSdf.parse("01/04/2018 15:00:00");
            validDate2 = validSdf.parse("01/04/2018 17:00:00");
            validDate3 = validSdf.parse("01/04/2018 16:00:00");
            validDate4 = validSdf.parse("03/12/2017 15:00:00");
            validDate5 = validSdf.parse("08/12/2017 15:00:00");
            validDate6 = validSdf.parse("19/12/2017 15:00:00");

        } catch (ParseException c) {
            c.printStackTrace();
        }

        // Sets up a valid temperature sensor with valid Readings.

        validTemperatureSensor = new Sensor("TempOne", new TypeSensor("Temperature", "Celsius"),
                new Local(21, 10, 15),
                new Date());
        Reading firstTempReading = new Reading(15, validDate1);
        Reading secondTempReading = new Reading(20, validDate2);
        Reading thirdTempReading = new Reading(30, validDate3);
        validTemperatureSensor.addReading(firstTempReading);
        validTemperatureSensor.addReading(secondTempReading);
        validTemperatureSensor.addReading(thirdTempReading);
        validSensorList.addSensor(validTemperatureSensor);


        // Sets up a valid rainfall sensor with valid readings.

        Sensor validRainfallSensor = new Sensor("RainOne", new TypeSensor("rainfall", "l/m2 "), new Local
                (21, 41, 11), new Date());
        Reading firstRainReading = new Reading(40, validDate4);
        Reading secondRainReading = new Reading(10, validDate5);
        Reading thirdRainReading = new Reading(10, validDate6);
        validRainfallSensor.addReading(firstRainReading);
        validRainfallSensor.addReading(secondRainReading);
        validRainfallSensor.addReading(thirdRainReading);
        validSensorList.addSensor(validRainfallSensor);
    }


    @Test
    void seeIfGetCurrentRoomTemperatureWorks() {
        // Arrange

        double expectedResult = 20;

        // Act

        double actualResult = controller.getCurrentRoomTemperature(validRoom);

        // Assert

        assertEquals(expectedResult, actualResult, 0.01);
    }

    @Test
    void SeeIfGetCurrentTemperatureInTheHouseAreaWorks() {
        // Arrange

        validHouseArea.setSensorList(validSensorList);
        validHouse.setMotherArea(validHouseArea);
        double expectedResult = 20;

        // Act

        double actualResult = controller.getHouseAreaTemperature(validHouse);


        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfGetAverageOfReadingsBetweenTwoGivenDates() {
        // Arrange
//        Date intervalStart = new GregorianCalendar(2017, Calendar.DECEMBER, 2).getTime();
//        Date intervalEnd = new GregorianCalendar(2017, Calendar.DECEMBER, 20).getTime();
        validHouseArea.setSensorList(validSensorList);
        double expectedResult = 25;

        // Act
        double actualResult = controller.getAverageRainfallInterval(validHouse, validDate4, validDate5);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getAverageRainfallIntervalThrowsExceptionReadingListEmpty() {
        //Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            Date day = new Date();
            try {
                day = validSdf.parse("07/11/2019 10:02:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            controller.getAverageRainfallInterval(validHouse, day, day);
        });
        //Assert
        assertEquals("Warning: Average value not calculated - No readings available.", exception.getMessage());
    }

    @Test
    void getAverageRainfallIntervalThrowsExceptionReadingListNull() {
        //Arrange
        validSensorList = null;
        //Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            Date date = new Date();
            try {
                date = validSdf.parse("07/11/2019 10:02:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date date2 = new Date();
            try {
                date2 = validSdf.parse("07/11/2019 10:02:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            controller.getAverageRainfallInterval(validHouse, date, date2);
        });
        //Assert
        assertEquals("Warning: Average value not calculated - No readings available.", exception.getMessage());
    }

    @Test
    void ensureThatWeGetTotalReadingsOnGivenDay() {
        // Arrange
        Date date = new Date();
        try {
            date = validSdf.parse("03/12/2017 10:02:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        validHouseArea.setSensorList(validSensorList);
        double expectedResult = 40;

        // Act
        double actualResult = controller.getTotalRainfallOnGivenDay(validHouse, date);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void ensureThatWeGetTotalReadingsOnGivenDayNoRainfall() {
        // Arrange
        validHouseArea.setSensorList(validSensorList);

        // Act
        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            Date date = new Date();
            try {
                date = validSdf.parse("03/12/2018 10:02:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            controller.getTotalRainfallOnGivenDay(validHouse, date);
        });

        // Assert
        assertEquals("Warning: Total value was not calculated - No readings were available.", exception.getMessage());
    }

    @Test
    void ensureThatWeGetTotalReadingsWithoutSensors() {
        // Arrange

        SensorList emptyList = new SensorList();
        validHouseArea.setSensorList(emptyList);

        // Act

        Throwable exception = assertThrows(IllegalStateException.class, () -> controller.getTotalRainfallOnGivenDay(validHouse, validDate4));

        // Assert

        assertEquals("Warning: Total value could not be calculated - No readings were available.", exception.getMessage());
    }

    @Test
    void ensureThatWeGetTotalReadingsWithoutRainFallSensorsAndWithoutReadings() {
        // Arrange
        Date date = new Date();
        try {
            date = validSdf.parse("02/02/2015 10:02:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SensorList temperatureList = new SensorList();
        Sensor temperatureSensor = new Sensor("temperature sensor", new TypeSensor("temperature", "celsius"), new Local(21, 20, 20), date);
        temperatureList.addSensor(temperatureSensor);
        validHouseArea.setSensorList(temperatureList);

        // Act

        Throwable exception = assertThrows(IllegalStateException.class, () -> controller.getTotalRainfallOnGivenDay(validHouse, validDate4));

        // Assert

        assertEquals("Warning: Total value could not be calculated - No readings were available.", exception.getMessage());
    }

    @Test
    void ensureThatWeGetTotalReadingsWithoutWithoutReadings() {
        // Arrange
        Date date = new Date();
        try {
            date = validSdf.parse("02/02/2015 10:02:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SensorList rainFallSensorList = new SensorList();
        Sensor rainfallSensor = new Sensor("rainfall sensor", new TypeSensor("rainfall", "L"), new Local(21, 20, 20), date);
        rainFallSensorList.addSensor(rainfallSensor);
        validHouseArea.setSensorList(rainFallSensorList);

        // Act

        Throwable exception = assertThrows(IllegalStateException.class, () -> controller.getTotalRainfallOnGivenDay(validHouse, validDate4));

        // Assert

        assertEquals("Warning: Total value could not be calculated - No readings were available.", exception.getMessage());
    }

    @Test
    void roomMaxTemperatureInGivenDay() {
        // Arrange

        Reading secondReading = new Reading(30, validDate4);
        Reading thirdReading = new Reading(3, validDate5);
        validTemperatureSensor.addReading(secondReading);
        validTemperatureSensor.addReading(thirdReading);
        double expectedResult = 30;

        // Act

        double actualResult = controller.getDayMaxTemperature(validRoom, validDate4);

        // Assert

        assertEquals(expectedResult, actualResult);

    }

    @Test
    void getRoomName() {
        // Arrange

        String expectedResult = "Bedroom";

        // Act

        String actualResult = controller.getRoomName(validRoom);

        // Assert

        assertEquals(expectedResult, actualResult);

    }
}