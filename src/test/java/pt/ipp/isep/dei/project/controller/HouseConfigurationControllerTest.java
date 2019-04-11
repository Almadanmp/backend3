package pt.ipp.isep.dei.project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pt.ipp.isep.dei.project.model.*;
import pt.ipp.isep.dei.project.repository.RoomRepository;
import pt.ipp.isep.dei.project.model.sensor.HouseSensor;
import pt.ipp.isep.dei.project.model.sensor.HouseSensorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;


/**
 * HouseConfigurationController tests class.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class HouseConfigurationControllerTest {

    // Common artifacts for testing in this class.

    @Mock
    private RoomService mockRoomRepository;

    @Mock
    private HouseSensorService mockHouseSensorRepository;

    private static final String PATH_TO_FRIDGE = "pt.ipp.isep.dei.project.model.device.devicetypes.FridgeType";
    private HouseConfigurationController controller = new HouseConfigurationController();
    private House validHouse;
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    @BeforeEach
    void arrangeArtifacts() {
        List<String> deviceTypeList = new ArrayList<>();
        Address address = new Address("Rua Dr. António Bernardino de Almeida", "431", "4200-072", "Porto", "Portugal");
        validHouse = new House("ISEP", address,
                new Local(20, 20, 20), 60, 180,
                deviceTypeList);
        validHouse.setMotherArea(new GeographicArea("Porto", new AreaType("Cidade"),
                2, 3, new Local(4, 4, 100)));
        deviceTypeList.add(PATH_TO_FRIDGE);
        roomService = new RoomService(roomRepository);
    }


    //USER STORY 105


    @Test
    void seeIfGetHouseName() {
        //Act

        String actualResult = controller.getHouseId(validHouse);

        // Assert

        assertEquals("ISEP", actualResult);
    }


    // US108

    @Test
    void seeIfPrintsRoomList() {
        // Arrange

              String expectedResult = "Invalid List - List is Empty\n";

        // Act

        String result = controller.buildRoomsString(roomService);

        // Assert

        assertEquals(expectedResult, result);
    }

    @Test
    void createsRoom() {
        // Act

        Room actualResult1 = controller.createNewRoom(roomService, "Kitchen", "Not equipped Kitchen", 1, 10, 15, 10, "Room1", "Grid1");
        Room actualResult2 = controller.createNewRoom(roomService, "Room", "Double Bedroom", 1, 10, 15, 10, "Room1", "Grid1");
        Room actualResult3 = controller.createNewRoom(roomService, "Kitchen", "Fully Equipped Kitchen", 1, 10, 15, 10, "Room1", "Grid1");

        // Assert

        assertTrue(actualResult1 instanceof Room);
        assertTrue(actualResult2 instanceof Room);
        assertTrue(actualResult3 instanceof Room);
    }

    @Test
    void addsRoom() {
        //Arrange
        Room room1 = new Room("Kitchen", "Not equipped Kitchen", 1, 10, 15, 10, "Room1", "Grid1");
        Room room2 = new Room("Room", "Double Bedroom", 1, 10, 15, 10, "Room1", "Grid1");

        // Act
        boolean actualResult1 = controller.addRoomToHouse(roomService, room1);
        boolean actualResult2 = controller.addRoomToHouse(roomService, room2);

        // Assert
        assertTrue(actualResult1);
        assertTrue(actualResult2);
    }

    @Test
    void seeIfSetHouseAddress() {
        //Act

        controller.setHouseAddress("Rua do ISEP", "431", "4400", "City", "Portugal", validHouse);

        // Assert

        assertEquals(validHouse.getAddress(), new Address("Rua do ISEP", "431", "4400", "City", "Portugal"));
    }

    @Test
    void seeIfSetHouseLocal() {
        //Act

        controller.setHouseLocal(10, 51, 2, validHouse);

        // Assert

        assertEquals(validHouse.getLocation(), new Local(10, 51, 2));
    }


    @Test
    void seeIfSetAndGetHouseMotherAreaWorks() {
        //Arrange
        controller.setHouseMotherArea(validHouse, new GeographicArea("Porto", new AreaType("Cidade"),
                2, 3, new Local(4, 4, 100)));
        GeographicArea expected = new GeographicArea("Porto", new AreaType("Cidade"),
                2, 3, new Local(4, 4, 100));
        //Act
        GeographicArea actualResult = validHouse.getMotherArea();
        //Assert
        assertEquals(expected, actualResult);
    }

    @Test
    void seeIfIsMotherAreaNullBothConditions() {
        // Act
        boolean actualResult1 = validHouse.isMotherAreaNull();
        controller.setHouseMotherArea(validHouse, null);
        boolean actualResult2 = validHouse.isMotherAreaNull();
        // Assert
        assertFalse(actualResult1);
        assertTrue(actualResult2);
    }

    @Test
    void seeIfReadSensorsWorks(){
        // Arrange

        String filePath = "sensorFiles/DataSet_sprint06_HouseSensors.json";

        // Mock the checking for Rooms

        Room B106 = new Room("B106", "Classroom", 3, 20, 20, 20,
                "Mock", "Mock");
        Optional<Room> optionalRoomB106 = Optional.of(B106);
        Mockito.when(mockRoomRepository.findByID("B106")).thenReturn(optionalRoomB106);

        Room B109 = new Room("B109", "Classroom", 3, 20, 20, 20,
                "Mock", "Mock");
        Optional<Room> optionalRoomB109 = Optional.of(B109);
        Mockito.when(mockRoomRepository.findByID("B109")).thenReturn(optionalRoomB109);

        Room B107 = new Room("B107", "Classroom", 3, 20, 20, 20,
                "Mock", "Mock");
        Optional<Room> optionalRoomB107 = Optional.of(B107);
        Mockito.when(mockRoomRepository.findByID("B107")).thenReturn(optionalRoomB107);

        Optional<Room> optionalRoomB405 = Optional.empty();
        Mockito.when(mockRoomRepository.findByID("B405")).thenReturn(optionalRoomB405);

        // Ignore the .save call, which is void.

        doNothing().when(mockHouseSensorRepository).save(isA(HouseSensor.class));

        // Expected result

        int expectedResult = 3;

        // Act

        int actualResult = controller.readSensors(filePath, mockRoomRepository, mockHouseSensorRepository);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfReadSensorsWorksEmptyDB(){
        // Arrange

        String filePath = "sensorFiles/DataSet_sprint06_HouseSensors.json";

        Mockito.when(mockRoomRepository.isEmptyDB()).thenReturn(true);

        int expectedResult = 0;

        // Act

        int actualResult = controller.readSensors(filePath, mockRoomRepository, mockHouseSensorRepository);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfReadSensorsWorksInvalidFile(){
        // Arrange

        String filePath = "houseFiles/DataSet_sprint06_HouseData.json";

        Mockito.when(mockRoomRepository.isEmptyDB()).thenReturn(false);

        int expectedResult = -1;

        // Act

        int actualResult = controller.readSensors(filePath, mockRoomRepository, mockHouseSensorRepository);

        // Assert

        assertEquals(expectedResult, actualResult);
    }


}
