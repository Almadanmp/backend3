package pt.ipp.isep.dei.project.dto.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.project.dto.EnergyGridDTO;
import pt.ipp.isep.dei.project.dto.PowerSourceDTO;
import pt.ipp.isep.dei.project.dto.RoomDTO;
import pt.ipp.isep.dei.project.model.energy.EnergyGrid;
import pt.ipp.isep.dei.project.model.energy.PowerSource;
import pt.ipp.isep.dei.project.model.room.Room;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class EnergyGridMapperTest {
    // Common testing artifacts for testing in this class

    private EnergyGrid validGrid;

    @BeforeEach
    void arrangeArtifacts() {
        validGrid = new EnergyGrid("GridOne", 21D, "34576");
        List<Room> roomService = new ArrayList<>();
        Room roomOne = new Room("Kitchen", "Equipped Kitchen", 1, 20, 30, 10, "Room1");
        Room roomTwo = new Room("Kitchen", "Equipped Kitchen", 1, 20, 30, 10, "Room1");
        roomService.add(roomOne);
        roomService.add(roomTwo);
        List<PowerSource> powerSourceList = new ArrayList<>();
        PowerSource powerSourceOne = new PowerSource("firstSource", 10, 30);
        PowerSource powerSourceTwo = new PowerSource("firstSource", 10, 30);
        powerSourceList.add(powerSourceOne);
        powerSourceList.add(powerSourceTwo);
        validGrid.setRooms(roomService);
        validGrid.setPowerSourceList(powerSourceList);
    }

    @Test
    void seeIfObjectToDTOWorks() {
        // Arrange

        EnergyGridDTO expectedResult = new EnergyGridDTO();
        expectedResult.setName("GridOne");
        expectedResult.setMaxContractedPower(21D);
        List<RoomDTO> roomList = new ArrayList<>();
        Room roomOne = new Room("Kitchen", "Equipped Kitchen", 1, 20, 30, 10, "Room1");
        roomList.add(RoomMapper.objectToDTO(roomOne));
        expectedResult.setRoomDTOS(roomList);
        List<PowerSourceDTO> powerSources = new ArrayList<>();
        PowerSource powerSourceOne = new PowerSource("firstSource", 10, 30);
        powerSources.add(PowerSourceMapper.objectToDTO(powerSourceOne));
        expectedResult.setPowerSourceDTOS(powerSources);

        // Act

        EnergyGridDTO actualResult = EnergyGridMapper.objectToDTO(validGrid);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfDTOToObjectWorks() {
        // Arrange

        EnergyGridDTO dtoToConvert = new EnergyGridDTO();
        dtoToConvert.setName("GridOne");
        dtoToConvert.setMaxContractedPower(21D);
        List<RoomDTO> roomList = new ArrayList<>();
        Room roomOne = new Room("Kitchen", "Equipped Kitchen", 1, 20, 30, 10, "Room1");
        roomList.add(RoomMapper.objectToDTO(roomOne));
        dtoToConvert.setRoomDTOS(roomList);
        List<PowerSourceDTO> powerSources = new ArrayList<>();
        PowerSource powerSourceOne = new PowerSource("firstSource", 10, 30);
        powerSources.add(PowerSourceMapper.objectToDTO(powerSourceOne));
        dtoToConvert.setPowerSourceDTOS(powerSources);

        // Act

        EnergyGrid actualResult = EnergyGridMapper.dtoToObject(dtoToConvert);

        // Assert

        assertEquals(validGrid, actualResult);

    }

    @Test
    void seeIfDtoToObjectEmptyListsWorks() {
        // Arrange

        EnergyGrid expectedResult = new EnergyGrid("Main Grid", 21D, "7");

        EnergyGridDTO dtoToConvert = new EnergyGridDTO();
        dtoToConvert.setName("Main Grid");
        dtoToConvert.setMaxContractedPower(21D);
        dtoToConvert.setHouseID("7");

        // Act

        EnergyGrid actualResult = EnergyGridMapper.dtoToObjectEmptyLists(dtoToConvert);

        // Assert

        assertEquals(expectedResult, actualResult);

    }


}
