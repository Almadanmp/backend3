package pt.ipp.isep.dei.project.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.Assert;
import pt.ipp.isep.dei.project.model.*;

import pt.ipp.isep.dei.project.model.device.Device;
import pt.ipp.isep.dei.project.model.device.DeviceList;
import pt.ipp.isep.dei.project.model.device.Fridge;
import pt.ipp.isep.dei.project.model.device.devicespecs.FridgeSpec;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * EnergyGridSettingsController tests class.
 */

class EnergyGridSettingsControllerTest {

    // Common artifacts for testing in this class.

    public static final String PATH_TO_FRIDGE = "pt.ipp.isep.dei.project.model.device.devicetypes.FridgeDT";
    private House validHouse;
    private EnergyGrid validGrid;
    private EnergyGridSettingsController controller = new EnergyGridSettingsController();

    @BeforeEach
    void arrangeArtifacts() {
        Address address = new Address("Rua Dr. António Bernardino de Almeida", "4200-072", "Porto");
        validHouse = new House("ISEP", address, new Local(20, 20, 20), new GeographicArea("Porto",
                new TypeArea("Cidade"), 2, 3, new Local(4, 4, 100)),
                60, 180, new ArrayList<>());
        validGrid = new EnergyGrid("validGrid", 300);
    }


    //US145


    @Test
    void seeIfRoomsPrint() {

        // Arrange

        RoomList roomList = new RoomList();
        Room room = new Room("Quarto", 1, 20, 2, 2);
        roomList.addRoom(room);
        validHouse.setRoomList(roomList);
        String expectedResult = "---------------\n" +
                "0) Designation: Quarto | House Floor: 1 | Width: 20.0 | Length: 2.0 | Height: 2.0\n" +
                "---------------\n";

        // Act

        String actualResult = controller.buildRoomsString(roomList);

        // Assert

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfRoomsPrintNull() {
        //Arrange

        String expectedResult = "The Room List wasn't properly initialized. Please try again.";

        //Act

        String actualResult = controller.buildRoomsString(null);

        //Assert

        Assert.assertEquals(expectedResult, actualResult);
    }

    //USER STORY 149

    @Test
    void seeIfRoomIsRemovedFromGrid() {

        //Arrange

        Room room = new Room("Quarto", 1, 20, 2, 2);
        validGrid.addRoomToAnEnergyGrid(room);

        //Act

        boolean actualresult = controller.removeRoomFromGrid(validGrid, room);

        //Assert

        Assertions.assertTrue(actualresult);
    }

    @Test
    void seeIfGridListPrints() {

        // Arrange

        EnergyGridList energyGridList = new EnergyGridList();
        energyGridList.addGrid(validGrid);
        validHouse.setEGList(energyGridList);
        String expectedResult = "---------------\n" +
                "0) Designation: validGrid | Max Power: 300.0\n" +
                "---------------\n";

        // Act

        String actualResult = controller.buildGridListString(validHouse);

        // Assert

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfRoomIsRemovedFromGridFalse() {

        //Arrange

        Room room = new Room("Quarto", 1, 20, 2, 2);

        //Act

        boolean actualresult = controller.removeRoomFromGrid(validGrid, room);

        //Assert

        assertFalse(actualresult);
    }

    @Test
    void ensureThatWeAddRoomToTheGrid() {

        // Arrange

        Room room = new Room("Quarto", 1, 20, 2, 2);
        EnergyGridList gridList = new EnergyGridList();
        gridList.addGrid(validGrid);
        RoomList rl = new RoomList();
        validGrid.setRoomList(rl);

        // Act

        boolean actualResult = controller.addRoomToGrid(validGrid, room);

        // Assert

        Assertions.assertTrue(actualResult);
    }

    @Test
    void ensureThatWeDoNotAddRoomToTheGrid() {

        // Arrange

        EnergyGridList gridList = new EnergyGridList();
        gridList.addGrid(validGrid);
        RoomList roomList = new RoomList();
        validGrid.setRoomList(roomList);
        Room room = new Room("Quarto", 1, 20, 2, 2);
        roomList.addRoom(room);

        // Act

        boolean actualResult = controller.addRoomToGrid(validGrid, room);

        // Assert

        assertFalse(actualResult);
    }

    @Test
    void seeIfAddPowerSourceToEnergyGridWorks() {
        // Arrange

        PowerSource powerSource = new PowerSource("PowerSourceOne", 10, 10);

        // Act

        boolean result = controller.addPowerSourceToGrid(validGrid, powerSource);

        // Assert

        Assertions.assertTrue(result);
    }

    @Test
    void seeIfAddPowerSourceToEnergyGridWorksFalse() {

        // Arrange

        PowerSource powerSource = new PowerSource("PowerSource", 20, 20);
        validGrid.addPowerSource(powerSource);

        // Act

        boolean result = controller.addPowerSourceToGrid(validGrid, powerSource);

        //Assert

        assertFalse(result);
    }

    @Test
    void seeIfAddEnergyGridToHouseWorks() {

        // Arrange

        EnergyGrid energyGrid = new EnergyGrid("grid", 400);


        // Act

        boolean actualResult = controller.addEnergyGridToHouse(validHouse, energyGrid);

        // Assert

        assertTrue(actualResult);
    }

    @Test
    void seeIfCreateGridTrue() {
        // Arrange

        EnergyGrid expectedResult = new EnergyGrid("EG1", 400);

        // Act

        EnergyGrid actualResult = controller.createEnergyGrid(validHouse, "EG1", 400);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeCreatePowerSource() {
        // Arrange

        PowerSource powerSource1 = new PowerSource("powersource1", 10, 10);
        PowerSource powerSource2 = new PowerSource("powersource2", 123, 76);

        // Act

        PowerSource actualResult1 = controller.createPowerSource(validGrid, "powersource1", 10, 10);
        PowerSource actualResult2 = controller.createPowerSource(validGrid, "powersource2", 123, 76);

        // Assert

        assertEquals(actualResult1, powerSource1);
        assertEquals(actualResult2, powerSource2);
    }

    @Test
    void testBuildListOfDevicesOrderedByTypeStringEmptyString() {
        //Arrange
        validHouse.addGrid(validGrid);
        //Act
        String expectedResult = "---------------\n" +
                "---------------\n";
        String actualResult = controller.buildListOfDevicesOrderedByTypeString(validGrid, validHouse);
        //Arrange
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void testBuildListOfDevicesOrderedByTypeStringWithDevices() {
        //Arrange
        List<String> deviceTypeString = new ArrayList<>();
        deviceTypeString.add(PATH_TO_FRIDGE);
        Address address = new Address("Rua das Flores", "4512", "Porto");
        House house = new House("casa de praia", address, new Local(4, 5, 4), new GeographicArea("porto", new TypeArea("cidade"), 2, 3, new Local(4, 4, 100)), 60, 180, deviceTypeString);
        Room room1EdC = new Room("B107", 1, 7, 11, 3.5);
        EnergyGrid eg = new EnergyGrid("Main Energy Grid Edificio C", 333);
        RoomList rl = new RoomList();
        DeviceList deviceList = new DeviceList();
        Device fridge = new Fridge(new FridgeSpec());
        deviceList.addDevice(fridge);
        room1EdC.setDeviceList(deviceList);
        eg.setRoomList(rl);
        rl.addRoom(room1EdC);
        //Act
        String expectedResult = "---------------\n" +
                "Device type: Fridge | Device name: null | Nominal power: 0.0 | Room: B107 | \n" +
                "---------------\n";
        String actualResult = controller.buildListOfDevicesOrderedByTypeString(eg, house);
        //Arrange
        assertEquals(expectedResult, actualResult);
    }
}