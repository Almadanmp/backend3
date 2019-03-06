package pt.ipp.isep.dei.project.dto;

import pt.ipp.isep.dei.project.model.*;


public class Mapper {
    private RoomDTO roomDTO = new RoomDTO();
    private GeographicAreaDTO geographicAreaDTO = new GeographicAreaDTO();
    private SensorDTO sensorDTO = new SensorDTO();

    public RoomDTO roomToDTO(Room room) {
        roomDTO.setRoomName(room.getRoomName());
        roomDTO.setHouseFloor(room.getHouseFloor());
        roomDTO.setRoomHeight(room.getRoomHeight());
        roomDTO.setRoomLength(room.getRoomLength());
        roomDTO.setRoomWidth(room.getRoomWidth());
        roomDTO.setId(room.getUniqueID());
        roomDTO.setRoomSensorList(room.getSensorList());
        roomDTO.setDeviceList(room.getDeviceList());
        return roomDTO;
    }

    public Room DTOtoRoom(RoomDTO roomDTO, House house) {
        Room room = null;
        RoomList roomlist = house.getRoomList();
        for (Room r : roomlist.getRooms()) {
            if (roomDTO.getId().compareTo(r.getUniqueID()) == 0) {
                r.setRoomName(roomDTO.getRoomName());
                r.setHouseFloor(roomDTO.getHouseFloor());
                r.setRoomWidth(roomDTO.getRoomWidth());
                r.setRoomLength(roomDTO.getRoomLength());
                r.setRoomHeight(roomDTO.getRoomHeight());
                r.setSensorList(roomDTO.getRoomSensorList());
                r.setDeviceList(roomDTO.getDeviceList());
                room = r;
            }
        }
        return room;
    }

    public GeographicArea createGeographicAreaFromDTO(GeographicAreaDTO geographicAreaDTO){
        GeographicArea geographicArea = new GeographicArea(geographicAreaDTO.getId(), geographicAreaDTO.getTypeArea(), geographicAreaDTO.getLength(),
                geographicAreaDTO.getWidth(), geographicAreaDTO.getLocation());
        geographicArea.setMotherArea(geographicAreaDTO.getMotherArea());
        geographicArea.setAreaSensors(geographicAreaDTO.getAreaSensors());
        geographicArea.setDescription(geographicAreaDTO.getDescription());
        geographicArea.setUniqueId(geographicAreaDTO.getUniqueId());
        return geographicArea;
    }

    public GeographicAreaDTO geographicAreaDTO(GeographicArea geographicArea) {
        geographicAreaDTO.setId(geographicArea.getId());
        geographicAreaDTO.setTypeArea(geographicArea.getTypeArea());
        geographicAreaDTO.setLength(geographicArea.getLength());
        geographicAreaDTO.setWidth(geographicArea.getWidth());
        geographicAreaDTO.setLocation(geographicArea.getLocation());
        geographicAreaDTO.setMotherArea(geographicArea.getMotherArea());
        geographicAreaDTO.setAreaSensors(geographicArea.getAreaSensors());
        geographicAreaDTO.setDescription(geographicArea.getDescription());
        geographicAreaDTO.setUniqueId(geographicArea.getUniqueID());
        return geographicAreaDTO;
    }
    public SensorDTO sensorToDTO(Sensor sensor) {
        sensorDTO.setName(sensor.getName());
        sensorDTO.setDateStartedFunctioning(sensor.getDateStartedFunctioning());
        sensorDTO.setLocal(sensor.getLocal());
        sensorDTO.setReadingList(sensor.getReadingList());
        sensorDTO.setTypeSensor(sensor.getTypeSensor());
        sensorDTO.setUniqueID(sensor.getUniqueID());
        return sensorDTO;
    }

    public Sensor DTOToGeoAreaSensor(SensorDTO sensorDTO, GeographicAreaDTO gaDTO) {
        Sensor sensor;
        GeographicArea ga = createGeographicAreaFromDTO(gaDTO);
        SensorList sensorList = ga.getSensorList();
        sensor = sensorListVoid(sensorDTO,sensorList);
        return sensor;
    }

    public Sensor DTOToHouseSensor(SensorDTO sensorDTO, House house) {
        Sensor sensor = null;
        for (Room room : house.getRoomList().getRooms()) {
            SensorList sensorList = room.getSensorList();
            sensor = sensorListVoid(sensorDTO,sensorList);
        }
        return sensor;
    }

    private Sensor sensorListVoid(SensorDTO sensorDTO, SensorList sensorList) {
        Sensor sensor = null;
        for (Sensor s : sensorList.getSensors()) {
            if (s.getUniqueID().compareTo(sensorDTO.getUniqueID()) == 0) {
                s.setName(sensorDTO.getName());
                s.setReadingList(sensorDTO.getReadingList());
                s.setLocal(sensorDTO.getLocal());
                s.setDateStartedFunctioning(sensorDTO.getDateStartedFunctioning());
                s.setTypeSensor(sensorDTO.getTypeSensor());
                s.setUniqueID(sensorDTO.getUniqueID());
                sensor = s;
            }
        }
        return sensor;
    }
}


