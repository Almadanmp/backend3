package pt.ipp.isep.dei.project.dto.mappers;

import pt.ipp.isep.dei.project.dto.EnergyGridDTO;
import pt.ipp.isep.dei.project.dto.PowerSourceDTO;
import pt.ipp.isep.dei.project.model.energy.EnergyGrid;
import pt.ipp.isep.dei.project.model.energy.PowerSource;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for converting Energy Grids and Energy Grid DTOs into one another.
 */

public final class EnergyGridMapper {
    /**
     * Don't let anyone instantiate this class.
     */

    private EnergyGridMapper() {
    }

    /**
     * This is the method that converts energy grid DTOs into model objects without PowerSource and Room Lists.
     *
     * @param dtoToConvert is the DTO we want to convert.
     * @return is the converted model object.
     */
    public static EnergyGrid dtoToObjectEmptyLists(EnergyGridDTO dtoToConvert) {
        // Update the name

        String objectName = dtoToConvert.getName();

        // Update the houseID

        String objectHouseID = dtoToConvert.getHouseID();

        // Update the maximum contracted power.

        double objectMaxContractedPower = dtoToConvert.getMaxContractedPower();

        // Create, update and return the new model object.

        return new EnergyGrid(objectName, objectMaxContractedPower, objectHouseID);
    }

    /**
     * This is the method that converts energy grid DTOs into model objects with the same data.
     *
     * @param dtoToConvert is the DTO we want to convert.
     * @return is the converted model object.
     */
    public static EnergyGrid dtoToObject(EnergyGridDTO dtoToConvert) {
        // Update the name

        String objectName = dtoToConvert.getName();

        // Update the houseID

        String objectHouseID = dtoToConvert.getHouseID();

        // Update the RoomList

        List<String> objectRoomService = new ArrayList<>();
        for (String y : dtoToConvert.getRoomIds()) {
            objectRoomService.add(y);
        }

        // Update the PowerSourceList

        List<PowerSource> objectPowerSourceList = new ArrayList<>();
        for (PowerSourceDTO y : dtoToConvert.getPowerSourceDTOS()) {
            PowerSource tempPowerSource = PowerSourceMapper.dtoToObject(y);
            objectPowerSourceList.add(tempPowerSource);
        }

        // Update the maximum contracted power.

        double objectMaxContractedPower = dtoToConvert.getMaxContractedPower();

        // Create, update and return the new model object.

        EnergyGrid resultObject = new EnergyGrid(objectName, objectMaxContractedPower, objectHouseID);
        resultObject.setRooms(objectRoomService);
        resultObject.setPowerSourceList(objectPowerSourceList);

        return resultObject;
    }

    public static EnergyGrid dtoToObjectWithNameRoomsAndPowerSources(EnergyGridDTO dtoToConvert) {
        // Update the name

        String objectName = dtoToConvert.getName();

        // Update the RoomList

        List<String> objectRoomService = new ArrayList<>();
        for (String y : dtoToConvert.getRoomIds()) {
            objectRoomService.add(y);
        }

        // Update the PowerSourceList

        List<PowerSource> objectPowerSourceList = new ArrayList<>();

        // Update House ID

        String houseID = dtoToConvert.getHouseID();

        // Create, update and return the new model object.

        EnergyGrid resultObject = new EnergyGrid();
        resultObject.setName(objectName);
        resultObject.setRooms(objectRoomService);
        resultObject.setPowerSourceList(objectPowerSourceList);
        resultObject.setHouseId(houseID);

        return resultObject;
    }

    /**
     * This is the method that converts energy grid model objects into DTOs with the same data.
     *
     * @param objectToConvert is the object we want to convert.
     * @return is the converted DTO.
     */

    public static EnergyGridDTO objectToDTO(EnergyGrid objectToConvert) {
        // Update the name

        String dtoName = objectToConvert.getName();

        // Update the houseID

        String dtoHouseID = objectToConvert.getHouseId();

        // Update the maximum contracted power

        Double dtoMaxContractedPower = objectToConvert.getMaxContractedPower();

        // Update the RoomList

        List<String> dtoRoomList = new ArrayList<>();
        for (String y : objectToConvert.getRoomIdList()) {
            if (!(dtoRoomList.contains(y))) {
                dtoRoomList.add(y);
            }
        }

        // Update the PowerSourceList

        List<PowerSourceDTO> dtoPowerSourceList = new ArrayList<>();
        for (PowerSource y : objectToConvert.getPowerSourceList()) {
            PowerSourceDTO tempPowerSourceDTO = PowerSourceMapper.objectToDTO(y);
            if (!(dtoPowerSourceList.contains(tempPowerSourceDTO))) {
                dtoPowerSourceList.add(tempPowerSourceDTO);
            }
        }

        // Create, update and return the new DTO object

        EnergyGridDTO resultDTO = new EnergyGridDTO();
        resultDTO.setMaxContractedPower(dtoMaxContractedPower);
        resultDTO.setName(dtoName);
        resultDTO.setPowerSourceDTOS(dtoPowerSourceList);
        resultDTO.setRoomIds(dtoRoomList);
        resultDTO.setHouseID(dtoHouseID);

        return resultDTO;
    }
}
