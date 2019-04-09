package pt.ipp.isep.dei.project.reader.wrapper;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pt.ipp.isep.dei.project.dto.AreaSensorDTO;
import pt.ipp.isep.dei.project.dto.LocalDTO;
import pt.ipp.isep.dei.project.dto.ReadingDTO;
import pt.ipp.isep.dei.project.reader.deserializer.CustomDateDeserializer;

import java.util.ArrayList;
import java.util.List;

public class AreaSensorDTOWrapper {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String typeSensor;

    @JsonProperty("units")
    private String units;
    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;

    @JsonProperty("altitude")
    private double altitude;

    @JsonProperty("location")
    private LocalDTO localDTO = new LocalDTO(latitude,longitude,altitude);

    @JsonProperty("start_date")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private String dateStartedFunctioning;

    private boolean active;

    private List<ReadingDTO> readingList;

    public AreaSensorDTOWrapper(){
        readingList = new ArrayList<>();
    }

    /**
     * Method that retrieves the DTO's id.
     *
     * @return is the DTO's id.
     */

    public String getId() {
        return id;
    }

    /**
     * Method that stores a String as the DTO's id.
     *
     * @param id is the string we want to store.
     */

    public void setId(String id) {
        this.id = id;
    }

    public LocalDTO getLocalDTO() {
        return localDTO;
    }

    public void setLocalDTO(LocalDTO localDTO) {
        this.localDTO = localDTO;
    }

    /**
     * Method that retrieves the units the Sensor stores readings in, as a String.
     *
     * @return is the unit the Sensor stores readings in.
     */

    public String getUnits() {
        return units;
    }

    /**
     * Method that retrieves the DTO's altitude as a double.
     *
     * @return is the DTO's altitude.
     */

    public double getAltitude() {
        return altitude;
    }

    /**
     * Method that retrieves the DTO's latitude as a double.
     *
     * @return is the DTO's latitude.
     */

    public double getLatitude() {
        return latitude;
    }

    /**
     * Method that retrieves the DTO's longitude as a double.
     *
     * @return is the DTO's longitude.
     */

    public double getLongitude() {
        return longitude;
    }

    /**
     * Method that stores a double as the DTO's altitude.
     *
     * @param altitude is the value we want to store.
     */

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    /**
     * Method that stores a double as the DTO's latitude.
     *
     * @param latitude is the value we want to store.
     */

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Method that stores a double as the DTO's longitude.
     *
     * @param longitude is the value we want to store.
     */

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Method that stores a String as the DTO's unit.
     *
     * @param units is string we want to store.
     */

    public void setUnits(String units) {
        this.units = units;
    }

    /**
     * Method that retrieves the DTO's name as a string.
     *
     * @return is the DTO's name.
     */

    public String getName() {
        return name;
    }

    /**
     * Method that stores a String as the DTO's name.
     *
     * @param name is the string we want to store.
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method that retrieves the DTO's type's name as a string.
     *
     * @return is a string that corresponds to the name of the type of the DTO.
     */

    public String getType() {
        return typeSensor;
    }

    /**
     * Method that stores a String as the DTO's type.
     *
     * @param typeSensor is the string we want to store.
     */
    public void setTypeSensor(String typeSensor) {
        this.typeSensor = typeSensor;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Method that retrieves the date at which the sensorDTO started functioning, as a string.
     *
     * @return the date at which the sensor started functioning, as a string.
     */

    public String getDateStartedFunctioning() {
        return dateStartedFunctioning;
    }

    /**
     * Method that stores a string as the date at which the DTO started functioning.
     *
     * @param dateStartedFunctioning is the date that we want to store.
     */

    public void setDateStartedFunctioning(String dateStartedFunctioning) {
        this.dateStartedFunctioning = dateStartedFunctioning;
    }

    public List<ReadingDTO> getReadingList() {
        return readingList;
    }

    public void setReadingList(List<ReadingDTO> readingList) {
        this.readingList = readingList;
    }

    @Override
    public boolean equals(Object testDTO) {
        if (this == testDTO) {
            return true;
        }
        if (!(testDTO instanceof AreaSensorDTO)) {
            return false;
        }
        AreaSensorDTO localVariable = (AreaSensorDTO) testDTO;
        return (localVariable.getId().equals(this.id) && localVariable.getName().equals(this.name));
    }

    @Override
    public int hashCode() {
        return 1;
    }
}

