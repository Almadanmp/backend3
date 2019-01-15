package pt.ipp.isep.dei.project.model;

import pt.ipp.isep.dei.project.model.devicetypes.DeviceType;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Class that represents a Room of a House.
 */

public class Room implements Metered {

    private String mRoomName;
    private int mHouseFloor;
    private double mRoomWidth;
    private double mRoomLength;
    private double mRoomHeight;
    private SensorList mRoomSensorList;
    private DeviceList mDeviceList;


    public Room(String name, int houseFloor, double width, double length, double height) {
        setRoomName(name);
        setRoomHouseFloor(houseFloor);
        setRoomWidth(width);
        setRoomLength(length);
        setRoomHeight(height);
        this.mRoomSensorList = new SensorList();
        this.mDeviceList = new DeviceList();
    }

    public SensorList getmRoomSensorList() {
        return mRoomSensorList;
    }

    private void setRoomName(String name) {
        mRoomName = name;
    }

    private void setRoomHouseFloor(int houseFloor) {
        mHouseFloor = houseFloor;
    }


    private void setRoomHeight(double height) {
        mRoomHeight = height;
    }

    private void setRoomLength(double length) {
        mRoomLength = length;
    }

    private void setRoomWidth(double width) {
        mRoomWidth = width;
    }

    double getRoomHeight() {
        return mRoomHeight;
    }

    double getRoomLength() {
        return mRoomLength;
    }

    double getRoomWidth() {
        return mRoomWidth;
    }

    public void setRoomSensorList(SensorList sensorList) {
        mRoomSensorList = sensorList;
    }

    public String getRoomName() {
        return mRoomName;
    }

    public int getHouseFloor() {
        return mHouseFloor;
    }

    public void setDeviceList(DeviceList deviceList) {
        this.mDeviceList = deviceList;
    }

    public List<Device> getDeviceList() {
        return this.mDeviceList.getDeviceList();
    }

    /**Method for printing all available devices in a room.
     * Used in US201 and US215
     * @return string with devices in room.
     */

    public String printDeviceList() {
        StringBuilder result = new StringBuilder("---------------\n");
        if (this.getDeviceList().isEmpty()) {
            return "This room has no devices on it\n";
        }
        for (int i = 0; i < this.getDeviceList().size(); i++) {
            Device device = this.getDeviceList().get(i);
            result.append("\n"+i).append(") Device Name: ").append(device.getName());
            result.append(", Device Type: ").append(device.getDeviceType());
            result.append(", Device Nominal Power: ").append(device.getNominalPower());
        }
        result.append("\n---------------\n");
        return result.toString();
    }

    public DeviceList getObjectDeviceList(){
        return this.mDeviceList;
    }

    /** This method will go through the room's device list and add all the devices'
     * The result is the room's total nominal power and will be returned as a double
     *
     * @return room's total nominal power (double)
     */
    public double getNominalPower() {
        double result = 0;
        for (Device d : this.getDeviceList()) {
            result += d.getNominalPower();
        }
        return result;
    }

    public double getMaxTemperatureInARoomOnAGivenDay(House house, Date day) {
        TypeSensor type = new TypeSensor("temperature", "Celsius");
        Sensor s = new Sensor("sensor1", type, house.getLocation(), new Date());
        for (int i = 0; i < mRoomSensorList.getSensors().length; i++) {
            s = mRoomSensorList.getSensors()[i];
        }
        return s.getReadingList().getMaximumOfGivenDayValueReadings(day);
    }

    boolean doesSensorListInARoomContainASensorByName(String name) {
        for (Sensor s : mRoomSensorList.getSensorList()) {
            if (s.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean removeDevice(Device device) {
        if ((mDeviceList.getDeviceList().contains(device))) {
            mDeviceList.getDeviceList().remove(device);
            return true;
        } else {
            return false;
        }
    }

    public boolean addSensor(Sensor sensor) {
        if (!(mRoomSensorList.getSensorList().contains(sensor))) {
            mRoomSensorList.getSensorList().add(sensor);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds a device to a room
     *
     * @param device to be added
     * @return the result of the operation (true if successful, false otherwise)
     */
    public boolean addDevice(Device device) {
        if (!(mDeviceList.getDeviceList().contains(device))) {
            mDeviceList.getDeviceList().add(device);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets most recent reading for current temperature.
     * @return 1
     */

    public double getCurrentRoomTemperature() {
        House h = new House();
        TypeSensor type = new TypeSensor("temperature", "Celsius");
        Sensor s = new Sensor("sensor1", type, h.getLocation(), new Date());
        for (int i = 0; i < mRoomSensorList.getSensors().length; i++) {
            s = mRoomSensorList.getSensors()[i];
        }
        return s.getReadingList().getMostRecentValueOfReading();
    }

    public String printRoom() {
        String result;
        result = this.mRoomName + ", " + this.getHouseFloor() + ", " +
                this.getRoomWidth() + ", " + this.getRoomLength() + ", " + this.getRoomHeight() + ".\n";
        return result;
    }

    /**
     * Returns the daily estimate consumption of all devices of a given type in this room.
     *
     * @param deviceType the device type
     * @return the sum of all daily estimate consumptions of that type
     */
    public double getDailyRoomConsumptionPerType(DeviceType deviceType) {
        double result = 0;
        for (Device d : getDeviceList()) {
            if (d.getDeviceType() == deviceType) {
                result += d.getDailyEstimateConsumption();
            }
        }
        return result;
    }

    public boolean addRoomDevicesToDeviceList(DeviceList list){
        for (Device d : this.getDeviceList()){
            if (!(list.containsDevice(d))){
                list.addDevice(d);
            }
        }
        return true;
    }

    public boolean removeRoomDevicesFromDeviceList(DeviceList list){
        if (list == null){
            return false;
        }
        for (Device d : this.getDeviceList()){
            if (list.containsDevice(d)){
                list.removeDevice(d);
            }
        }
        return true;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        return Objects.equals(mRoomName, room.mRoomName);
    }

    @Override
    public int hashCode() {
        return 1;
    }
}



