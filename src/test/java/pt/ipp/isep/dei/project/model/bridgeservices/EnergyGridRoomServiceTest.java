package pt.ipp.isep.dei.project.model.bridgeservices;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.ipp.isep.dei.project.dto.RoomDTOMinimal;
import pt.ipp.isep.dei.project.dto.mappers.RoomMinimalMapper;
import pt.ipp.isep.dei.project.model.Local;
import pt.ipp.isep.dei.project.model.device.Device;
import pt.ipp.isep.dei.project.model.device.Fridge;
import pt.ipp.isep.dei.project.model.device.devicespecs.FridgeSpec;
import pt.ipp.isep.dei.project.model.device.log.Log;
import pt.ipp.isep.dei.project.model.device.log.LogList;
import pt.ipp.isep.dei.project.model.energy.EnergyGrid;
import pt.ipp.isep.dei.project.model.energy.EnergyGridRepository;
import pt.ipp.isep.dei.project.model.geographicarea.GeographicArea;
import pt.ipp.isep.dei.project.model.house.Address;
import pt.ipp.isep.dei.project.model.house.House;
import pt.ipp.isep.dei.project.model.room.Room;
import pt.ipp.isep.dei.project.model.room.RoomRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EnergyGridRoomServiceTest {

    @Mock
    RoomRepository roomRepository;
    @Mock
    EnergyGridRepository energyGridRepository;

    @InjectMocks
    private EnergyGridRoomService energyGridRoomService;


    // Common artifacts for testing in this class.
    private static final String PATH_TO_FRIDGE = "pt.ipp.isep.dei.project.model.device.devicetypes.FridgeType";
    private EnergyGrid validGrid;
    private Device validFridge;
    private Room validRoom;
    private EnergyGrid firstValidGrid;
    List<Room> rooms = new ArrayList<>();

    @BeforeEach
    void arrangeArtifacts() {
        MockitoAnnotations.initMocks(this);
        firstValidGrid = new EnergyGrid("Primary Grid", 500D, "CasaUm");

        List<String> deviceTypeString = new ArrayList<>();
        deviceTypeString.add(PATH_TO_FRIDGE);
        House validHouse = new House("ISEP", new Address("Rua Dr. António Bernardino de Almeida", "431",
                "4455-125", "Porto", "Portugal"),
                new Local(20, 20, 20), 60,
                180, deviceTypeString);
        GeographicArea geographicArea = new GeographicArea("Porto", "Cidade",
                2, 3, new Local(4, 4, 100));
        validHouse.setMotherAreaID(geographicArea.getId());
        validGrid = new EnergyGrid("FirstGrid", 400D, "34576");
        validFridge = new Fridge(new FridgeSpec());
        validFridge.setNominalPower(20);
        validFridge.setAttributeValue(FridgeSpec.FREEZER_CAPACITY, 200D);
        validFridge.setAttributeValue(FridgeSpec.REFRIGERATOR_CAPACITY, 200D);
        validFridge.setAttributeValue(FridgeSpec.ANNUAL_CONSUMPTION, 200D);
        Log log = new Log(200, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 3).getTime());
        validFridge.addLog(log);
        validRoom = new Room("Office", "2nd Floor Office", 2, 30, 30, 10, "Room1");
        validRoom.addDevice(validFridge);
        energyGridRoomService.addRoom(validGrid, validRoom);
        EnergyGrid validGrid2 = new EnergyGrid("FirstGrid", 400D, "34576");
        Room validRoom2 = new Room("Office", "2nd Floor Office", 2, 30, 30, 10, "Room1");
        energyGridRoomService.addRoom(validGrid2, validRoom2);
    }

    @Test
    void seeIfRemoveRoomFromGridWorksWhenNoGrids() {
        // Arrange

        List<EnergyGrid> emptyList = new ArrayList<>();

        Mockito.when(energyGridRepository.getAllGrids()).thenReturn(emptyList);

        //Act

        boolean actualResult = energyGridRoomService.removeRoomFromGrid("roomID");

        // Assert

        assertFalse(actualResult);
    }

    @Test
    void seeIfRemoveRoomFromGridWorksWhenRoomIsInSecondGrid() {
        // Arrange

        List<EnergyGrid> twoGrids = new ArrayList<>();

        EnergyGrid firstGrid = new EnergyGrid("FirstGrid", 400D, "34576");
        EnergyGrid secondGrid = new EnergyGrid("SecondGrid", 400D, "34576");

        secondGrid.addRoomId("Room1");
        secondGrid.addRoomId("Room2");

        twoGrids.add(secondGrid);
        twoGrids.add(firstGrid);

        Mockito.when(energyGridRepository.getAllGrids()).thenReturn(twoGrids);
        Mockito.when(energyGridRepository.addGrid(secondGrid)).thenReturn(secondGrid);

        //Act

        boolean actualResult = energyGridRoomService.removeRoomFromGrid("Room2");

        // Assert

        assertTrue(actualResult);
    }

    @Test
    void seeIfRemoveRoomFromGridsWorksWhenRoomDoesNotExist() {
        // Arrange

        List<EnergyGrid> twoGrids = new ArrayList<>();

        EnergyGrid firstGrid = new EnergyGrid("FirstGrid", 400D, "34576");
        EnergyGrid secondGrid = new EnergyGrid("SecondGrid", 400D, "34576");

        secondGrid.addRoomId("Room1");
        secondGrid.addRoomId("Room2");

        twoGrids.add(secondGrid);
        twoGrids.add(firstGrid);

        Mockito.when(energyGridRepository.getAllGrids()).thenReturn(twoGrids);

        //Act

        boolean actualResult = energyGridRoomService.removeRoomFromGrid("Room3");

        // Assert

        assertFalse(actualResult);
    }


    @Test
    void seeIfGetRoomDTOMinimalByIdWorksWhenRoomDoesNotExitInGrid() {
        // Act

        validGrid.addRoomId("validRoom2");

        Room validRoom2 = new Room("validRoom2", "Single Bedroom", 19, 23456789, 5, 3, "Room1");
        RoomDTOMinimal validDTO1 = RoomMinimalMapper.objectToDtoWeb(validRoom);
        RoomDTOMinimal validDTO2 = RoomMinimalMapper.objectToDtoWeb(validRoom2);

        List<RoomDTOMinimal> list = new ArrayList<>();
        list.add(validDTO1);
        list.add(validDTO2);

        List<Room> list2 = new ArrayList<>();
        list2.add(validRoom);
        list2.add(validRoom2);


        Mockito.when(energyGridRepository.getById("Primary Grid")).thenReturn(validGrid);
        Mockito.when(roomRepository.getAllRooms()).thenReturn(list2);


        Mockito.when(roomRepository.getAllRooms()).thenReturn(list2);

        //Act

        RoomDTOMinimal actualResult = energyGridRoomService.getMinimalRoomDTOById("Primary Grid", "invalid Room");

        // Assert

        assertNull(actualResult);
    }

    @Test
    void seeIfGetRoomDTOMinimalByIdWorks() {
        // Act

        validGrid.addRoomId("validRoom2");

        Room validRoom2 = new Room("validRoom2", "Single Bedroom", 19, 23456789, 5, 3, "Room1");
        RoomDTOMinimal validDTO1 = RoomMinimalMapper.objectToDtoWeb(validRoom);
        RoomDTOMinimal validDTO2 = RoomMinimalMapper.objectToDtoWeb(validRoom2);

        List<RoomDTOMinimal> list = new ArrayList<>();
        list.add(validDTO1);
        list.add(validDTO2);

        List<Room> list2 = new ArrayList<>();
        list2.add(validRoom);
        list2.add(validRoom2);


        Mockito.when(energyGridRepository.getById("Primary Grid")).thenReturn(validGrid);
        Mockito.when(roomRepository.getAllRooms()).thenReturn(list2);


        Mockito.when(roomRepository.getAllRooms()).thenReturn(list2);

        //Act

        RoomDTOMinimal actualResult = energyGridRoomService.getMinimalRoomDTOById("Primary Grid", "validRoom2");

        // Assert

        assertEquals(validDTO2, actualResult);
    }

    @Test
    void seeIfGetRoomDTOMinimalInGridWorks() {
        // Act

        Room validRoom2 = new Room("validRoom2", "Single Bedroom", 19, 23456789, 5, 3, "Room1");
        RoomDTOMinimal validDTO1 = RoomMinimalMapper.objectToDtoWeb(validRoom);
        RoomDTOMinimal validDTO2 = RoomMinimalMapper.objectToDtoWeb(validRoom2);

        List<RoomDTOMinimal> list = new ArrayList<>();
        list.add(validDTO1);
        list.add(validDTO2);

        List<Room> list2 = new ArrayList<>();
        list2.add(validRoom);
        list2.add(validRoom2);


        Mockito.when(energyGridRepository.getById("Primary Grid")).thenReturn(validGrid);
        validGrid.addRoomId("validRoom2");

        Mockito.when(roomRepository.getAllRooms()).thenReturn(list2);

        //Act

        List<RoomDTOMinimal> actualResult = energyGridRoomService.getRoomsDtoWebInGrid("Primary Grid");

        // Assert

        assertEquals(list, actualResult);
    }

    @Test
    void seeIfRemoveRoomFromGridWorksWhenRoomIsNotInGrid() {
        // Act

        Room validRoom2 = new Room("validRoom2", "Single Bedroom", 19, 23456789, 5, 3, "Room1");

        List<Room> list = new ArrayList<>();
        list.add(validRoom2);

        Mockito.when(energyGridRepository.getById("Primary Grid")).thenReturn(validGrid);

        //Act

        boolean actualResult = energyGridRoomService.removeRoomFromGrid("validRoom2", "Primary Grid");

        // Assert

        assertFalse(actualResult);
    }

    @Test
    void seeIfRemoveRoomFromGridWorks() {
        // Act

        Room validRoom2 = new Room("validRoom2", "Single Bedroom", 19, 23456789, 5, 3, "Room1");

        List<Room> list = new ArrayList<>();
        list.add(validRoom2);

        validGrid.addRoomId("validRoom2");

        Mockito.when(energyGridRepository.getById("Primary Grid")).thenReturn(validGrid);

        //Act

        boolean actualResult = energyGridRoomService.removeRoomFromGrid("validRoom2", "Primary Grid");

        // Assert

        assertTrue(actualResult);
    }

    @Test
    void seeIfGetRoomByIdWorks() {
        // Act

        Room validRoom2 = new Room("validRoom2", "Single Bedroom", 19, 23456789, 5, 3, "Room1");
        Mockito.when(roomRepository.findRoomByID("validRoom2")).thenReturn(Optional.of(validRoom2));

        //Act

        Room actualResult = energyGridRoomService.getRoomById("validRoom2");

        // Assert

        assertEquals(validRoom2, actualResult);
    }

    @Test
    void seeIfGetRoomByIdReturnsException() {
        // Act

        Mockito.when(roomRepository.findRoomByID("validRoom2")).thenReturn(Optional.empty());

        //Act & Assert

        assertThrows(NoSuchElementException.class,
                () -> energyGridRoomService.getRoomById("validRoom2"));
    }

    @Test
    void seeIfAttachRoomToGridWorksWhenRoomExistsAlready() {
        // Act

        Room validRoom2 = new Room("validRoom2", "Single Bedroom", 19, 23456789, 5, 3, "Room1");
        validGrid.addRoomId("validRoom2");
        Mockito.when(energyGridRepository.getById("FirstGrid")).thenReturn(validGrid);
        Mockito.when(roomRepository.findRoomByID("Office")).thenReturn(Optional.of(validRoom2));

        //Act

        boolean actualResult = energyGridRoomService.attachRoomToGrid("Office", "FirstGrid");

        // Assert

        assertFalse(actualResult);
    }

    @Test
    void seeIfAttachRoomToGridWorks() {
        // Act

        Room validRoom2 = new Room("validRoom2", "Single Bedroom", 19, 23456789, 5, 3, "Room1");

        Mockito.when(energyGridRepository.getById("FirstGrid")).thenReturn(validGrid);
        Mockito.when(roomRepository.findRoomByID("Office")).thenReturn(Optional.of(validRoom2));
        Mockito.when(energyGridRepository.addGrid(validGrid)).thenReturn(validGrid);

        //Act

        boolean actualResult = energyGridRoomService.attachRoomToGrid("Office", "FirstGrid");

        // Assert

        assertTrue(actualResult);
    }

    @Test
    void seeIfRemovesRoom() {
        // Act
        validGrid.addRoomId(validRoom.getId());
        rooms.add(validRoom);

        boolean actualResult = energyGridRoomService.removeRoom(validGrid, validRoom);

        // Assert

        assertTrue(actualResult);
    }

    @Test
    void seeIfRemovesRoomFails() {
        // Arrange

        validGrid.addRoomId(validRoom.getId());

        energyGridRoomService.removeRoom(validGrid, validRoom);

        // Act

        boolean actualResult = energyGridRoomService.removeRoom(validGrid, validRoom);

        // Assert

        assertFalse(actualResult);
    }

    @Test
    void seeIfAddRoomToGridWorks() {
        // Arrange

        Room testRoom = new Room("Kitchen", "Equipped Kitchen", 1, 20, 20, 10, "Room1");

        // Act

        boolean actualResult = energyGridRoomService.addRoom(validGrid, testRoom);


        // Assert

        assertTrue(actualResult);
    }

    @Test
    void seeIfAddRoomToGridDoesNotWork() {
        // Arrange

        Room testRoom = new Room("Kitchen", "Equipped Kitchen", 1, 20, 20, 10, "Room1");

        // Act

        validGrid.addRoomId(testRoom.getId());
        rooms.add(testRoom);

        energyGridRoomService.addRoom(validGrid, testRoom);
        boolean actualResult = energyGridRoomService.addRoom(validGrid, testRoom);

        // Assert

        assertFalse(actualResult);
    }

    @Test
    void seeIfGetNominalPowerWorksMultipleRooms() {
        //Arrange

        double expectedResult = 40;
        Room extraRoom = new Room("Kitchen", "Equipped Kitchen", 0, 12, 30, 10, "Room1");
        extraRoom.addDevice(validFridge);
        energyGridRoomService.addRoom(validGrid, extraRoom);
        rooms.add(validRoom);
        rooms.add(extraRoom);
        validGrid.addRoomId(validRoom.getId());
        validGrid.addRoomId(extraRoom.getId());

        Mockito.when(roomRepository.getAllRooms()).thenReturn(rooms);
        //Act

        double actualResult = energyGridRoomService.getNominalPower(validGrid);

        //Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfPrintDevicesWorks() {
        // Arrange

        String expectedResult = "---------------\n" +
                "0) device Name: null, device Type: Fridge, device Nominal Power: 20.0\n" +
                "---------------\n";


        rooms.add(validRoom);
        validGrid.addRoomId(validRoom.getId());

        Mockito.when(roomRepository.getAllRooms()).thenReturn(rooms);
        // Act

        String actualResult = energyGridRoomService.buildDeviceListString(validGrid);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfDeviceListPrintsByTypeWorks() {
        // Arrange

        String expectedResult = "---------------\n" +
                "Device type: Fridge | Device name: null | Nominal power: 20.0 | Room: Office | \n" +
                "---------------\n";

        // Act

        rooms.add(validRoom);
        validGrid.addRoomId(validRoom.getId());

        Mockito.when(roomRepository.getAllRooms()).thenReturn(rooms);

        String actualResult = energyGridRoomService.buildDeviceListWithTypeString(validGrid);

        // Assert

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfDeviceListPrintsByTypeWorksEmpty() {
        // Arrange

        EnergyGrid testGrid = new EnergyGrid("EmptyGrid", 100D, "34576");
        String expectedResult = "---------------\n" +
                "---------------\n";

        // Act

        String actualResult = energyGridRoomService.buildDeviceListWithTypeString(testGrid);

        // Assert

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfGetsLogInInterval() {
        // Arrange
        SimpleDateFormat validSdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date initialTime = new Date();
        try {
            initialTime = validSdf.parse("11/01/2018 10:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date finalTime = new Date();
        try {
            finalTime = validSdf.parse("11/03/2018 10:30:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date logDate = new Date();
        try {
            logDate = validSdf.parse("20/02/2018 10:30:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log validLog = new Log(300, logDate, new GregorianCalendar
                (2018, Calendar.FEBRUARY, 20, 10, 30).getTime());
        validFridge.addLog(validLog);
        LogList expectedResult = new LogList();
        expectedResult.addLog(validLog);

        rooms.add(validRoom);
        validGrid.addRoomId(validRoom.getId());

        Mockito.when(roomRepository.getAllRooms()).thenReturn(rooms);

        // Act

        LogList actualResult = energyGridRoomService.getLogsInInterval(validGrid, initialTime, finalTime);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfDoesNotGetLogInInterval() {
        // Arrange
        SimpleDateFormat validSdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date initialTime = new Date();
        try {
            initialTime = validSdf.parse("11/01/2018 10:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date finalTime = new Date();
        try {
            finalTime = validSdf.parse("11/03/2018 10:30:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LogList expectedResult = new LogList();

        rooms.add(validRoom);
        validGrid.addRoomId(validRoom.getId());

        Mockito.when(roomRepository.getAllRooms()).thenReturn(rooms);

        // Act

        LogList actualResult = energyGridRoomService.getLogsInInterval(validGrid, initialTime, finalTime);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getByIndexWithEmptyDeviceList() {
        //Arrange

        EnergyGrid emptyGrid = new EnergyGrid("emptyGrid", 330D, "34576");


        //Act

        Throwable exception = assertThrows(IndexOutOfBoundsException.class, () -> energyGridRoomService.getDeviceByIndex(0, emptyGrid));

        //Assert

        assertEquals("The device list is empty.", exception.getMessage());
    }

    @Test
    void seeIfGetDeviceByIndexWorks() {
        //Act

        rooms.add(validRoom);
        validGrid.addRoomId(validRoom.getId());

        Mockito.when(roomRepository.getAllRooms()).thenReturn(rooms);
        Device actualResult = energyGridRoomService.getDeviceByIndex(0, validGrid);

        //Assert

        assertEquals(validFridge, actualResult);
    }

    @Test
    void seeIfGetRoomWorks() {
        // Mockito.when(energyGridRepo.getAllRooms()())

        rooms.add(validRoom);
        validGrid.addRoomId(validRoom.getId());

        Mockito.when(roomRepository.getAllRooms()).thenReturn(rooms);
        Room actualResult = energyGridRoomService.getRoom(validGrid, 0);
        assertEquals(validRoom, actualResult);
    }

    @Test
    void seeIfGetRoomThrowsException() {
        //Arrange

        EnergyGrid emptyGrid = new EnergyGrid("noRooms", 330D, "34576");

        //Act

        Throwable exception = assertThrows(IndexOutOfBoundsException.class, () -> energyGridRoomService.getRoom(emptyGrid, 0));

        //Assert

        assertEquals("The room list is empty.", exception.getMessage());
    }

    @Test
    void seeIfGetNumberOfDevicesWorks() {
        //Arrange

        EnergyGrid emptyList = new EnergyGrid("noDevices", 200D, "34576");
        rooms.add(validRoom);
        validGrid.addRoomId(validRoom.getId());

        Mockito.when(roomRepository.getAllRooms()).thenReturn(rooms);

        //Act

        int actualResult1 = energyGridRoomService.getNumberOfDevices(emptyList);
        int actualResult2 = energyGridRoomService.getNumberOfDevices(validGrid);


        //Assert

        assertEquals(0, actualResult1);
        assertEquals(1, actualResult2);
    }

    @Test
    void seeIfIsDeviceListEmptyWorks() {
        //Arrange
        rooms.add(validRoom);
        validGrid.addRoomId(validRoom.getId());
        energyGridRoomService.addRoom(validGrid, validRoom);

        Mockito.when(roomRepository.getAllRooms()).thenReturn(rooms);

        EnergyGrid nullList = new EnergyGrid("noDevices", 200D, "34576");
        EnergyGrid emptyList = new EnergyGrid("noDevices", 200D, "34576");

        //Act

        boolean actualResult1 = energyGridRoomService.isDeviceListEmpty(nullList);
        boolean actualResult2 = energyGridRoomService.isDeviceListEmpty(validGrid);
        boolean actualResult3 = energyGridRoomService.isDeviceListEmpty(emptyList);


        //Assert

        assertTrue(actualResult1);
        assertFalse(actualResult2);
        assertTrue(actualResult3);
    }

    @Test
    void seeIfIsDeviceListEmptyWorksWhenListIsEmpty() {
        //Arrange

        Room room1 = new Room("room1", "Single Bedroom", 19, 23456789, 5, 3, "Room1");

        List<Room> list = new ArrayList<>();
        list.add(room1);

        Mockito.when(roomRepository.getAllRooms()).thenReturn(list);
        validGrid.addRoomId("Office");

        //Act

        boolean actualResult = energyGridRoomService.isDeviceListEmpty(validGrid);

        //Assert

        assertTrue(actualResult);
    }

    @Test
    void seeIfGetGridConsumptionInIntervalWorks() {

        rooms.add(validRoom);
        validGrid.addRoomId(validRoom.getId());

        Mockito.when(roomRepository.getAllRooms()).thenReturn(rooms);
        //Act With Consumption
        double actualResult = energyGridRoomService.getGridConsumptionInInterval(validGrid, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 3).getTime());
        //Act Without Consumption
        double actualResult1 = energyGridRoomService.getGridConsumptionInInterval(validGrid, new GregorianCalendar(2019, Calendar.JANUARY, 3).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 4).getTime());
        //Assert With Consumption
        assertEquals(200, actualResult);
        //Assert Without Consumption
        assertEquals(0, actualResult1);
    }

    @Test
    void seeIfBuildEnergyGridRoomsAsStringWorks() {

        //Arrange
        EnergyGrid energyGrid = new EnergyGrid();
        String expectedResult = "---------------\n" +
                "0) ID: Office | Description: 2nd Floor Office |\n" +
                "---------------\n";
        String expectedResult1 = "Invalid List - List is Empty\n";

        rooms.add(validRoom);
        validGrid.addRoomId(validRoom.getId());

        Mockito.when(roomRepository.getAllRooms()).thenReturn(rooms);
        //Act
        String actualResult = energyGridRoomService.buildEnergyGridRoomsAsString(validGrid);
        String actualResult1 = energyGridRoomService.buildEnergyGridRoomsAsString(energyGrid);
        //Assert
        assertEquals(expectedResult, actualResult);
        assertEquals(expectedResult1, actualResult1);
    }

    @Test
    void seeIfGetRoomListWorks() {
        //Arrange
        List<Room> roomList = new ArrayList<>();
        roomList.add(validRoom);

        rooms.add(validRoom);
        validGrid.addRoomId(validRoom.getId());

        Mockito.when(roomRepository.getAllRooms()).thenReturn(rooms);
        //Act
        List<Room> actualResult = energyGridRoomService.getRoomList(validGrid);
        //Assert
        assertEquals(roomList, actualResult);
    }

    @Test
    void seeIfRemoveRoomWorksWithoutRooms() {
        // Arrange

        energyGridRoomService.removeRoom(validGrid, validRoom);

        // Act

        boolean actualResult = energyGridRoomService.removeRoom(validGrid, validRoom);

        // Assert

        assertFalse(actualResult);
    }

    @Test
    void seeIfRemoveRoomByIdWorksWithManyRooms() {
        // Arrange

        Room room = new Room("Toilet", "bucket", 2, 2, 2, 2, "7");
        energyGridRoomService.addRoom(validGrid, room);

        rooms.add(validRoom);
        rooms.add(room);
        validGrid.addRoomId(validRoom.getId());
        validGrid.addRoomId(room.getId());

        Mockito.when(roomRepository.getAllRooms()).thenReturn(rooms);

        // Act

        boolean actualResult = energyGridRoomService.removeRoomById(validGrid, room.getId());

        // Assert

        assertTrue(actualResult);
    }

    @Test
    void seeIfRemoveRoomByIdFails() {
        // Arrange

        Room room = new Room("Toilet", "bucket", 2, 2, 2, 2, "7");
        energyGridRoomService.addRoom(validGrid, room);

        rooms.add(validRoom);
        rooms.add(room);
        validGrid.addRoomId(validRoom.getId());
        validGrid.addRoomId(room.getId());

        Mockito.when(roomRepository.getAllRooms()).thenReturn(rooms);
        energyGridRoomService.removeRoomById(validGrid, room.getId());

        // Act

        boolean actualResult = energyGridRoomService.removeRoomById(validGrid, room.getId());

        // Assert

        assertFalse(actualResult);
    }

    @Test
    void seeIfRemoveRoomByIdWorks() {
        // Arrange

        Room room = new Room("Toilet", "bucket", 2, 2, 2, 2, "7");
        energyGridRoomService.addRoom(validGrid, room);

        rooms.add(room);
        validGrid.addRoomId(room.getId());

        Mockito.when(roomRepository.getAllRooms()).thenReturn(rooms);

        // Act

        boolean actualResult = energyGridRoomService.removeRoomById(validGrid, room.getId());

        // Assert

        assertTrue(actualResult);
    }

}