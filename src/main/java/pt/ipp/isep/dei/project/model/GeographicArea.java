package pt.ipp.isep.dei.project.model;

import pt.ipp.isep.dei.project.model.sensor.AreaSensor;
import pt.ipp.isep.dei.project.model.sensor.AreaSensorService;

import javax.persistence.*;

/**
 * Class that represents a Geographical Area.
 */

@Entity
public class GeographicArea {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_area_id")
    private AreaType areaType;

    private double length;
    private double width;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mother_area_id")
    private GeographicArea motherArea;

    @Embedded
    private Local location;

    @Transient
//    @OneToOne(cascade = CascadeType.ALL)
    private AreaSensorService areaSensors;

    private String description;


    // GeoArea constructors. The minimum amount of data for a GeoArea is a place and a type of area.
    // They can be made with or without a sensor list.

    /**
     * Empty Constructor to use when importing Geographic Areas from XML files.
     */
    public GeographicArea() {
        this.areaSensors = new AreaSensorService();
    }

    /**
     * Constructor
     *
     * @param name     the name of the Area
     * @param areaType the type of the area.
     * @param length   the total length of the area.
     * @param width    the total width of the area.
     * @param location the location of the area,
     */

    public GeographicArea(String name, AreaType areaType, double length, double width, Local location) {
        this.name = name;
        this.areaType = areaType;
        this.length = length;
        this.width = width;
        this.location = location;
        this.areaSensors = new AreaSensorService();
    }

    /**
     * Standard getter method, to return the Id of the Geographical Area.
     *
     * @return the long with the Id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Standard setter method, to define the Id.
     *
     * @param id is the Id of the Geographical Area.
     */
    public void setId(Long id) {
        this.id = id;
    }

    // Setters and Getters for all the parameters.

    /**
     * Standard setter method, to define the name of the Geographical Area.
     *
     * @param name is the name of the Geographical Area.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Standard setter method, to define the Area type.
     *
     * @param areaType is the type of the Geographical Area.
     */
     void setAreaType(AreaType areaType) {
        this.areaType = areaType;
    }

    /**
     * Standard setter method, to define the length of the Geographic Area.
     *
     * @param length is the length value of the Geographical Area.
     */
    public void setLength(double length) {
        this.length = length;
    }

    /**
     * Standard setter method, to define the width of the Geographic Area.
     *
     * @param width is the width value of the Geographical Area.
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Standard getter method, to return the Location of the Geographical Area.
     *
     * @return the Location of the Geographical Area.
     */
    public Local getLocation() {
        return location;
    }

    /**
     * Standard setter method, to define the location of the Geographic Area.
     *
     * @param location is the Local of the Geographical Area.
     */
    public void setLocation(Local location) {
        this.location = location;
    }

    /**
     * Standard getter method, to return the name of the Geographical Area.
     *
     * @return the string with the Geographical Area name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the description attribute
     *
     * @param description String is a short description of the Geographical Area.
     */

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Standard getter method, to return the description of the Geographical Area.
     *
     * @return the string with the description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Setter for Sensor List.
     *
     * @param listToSet list to set
     */
    public void setSensorList(AreaSensorService listToSet) {
        this.areaSensors = listToSet;
    }

    /**
     * Standard setter method, to define the Geographical Area that contains the current Geographic Area.
     *
     * @param geoArea is the Geographical Area that contains this Geographical Area.
     */
    public boolean setMotherArea(GeographicArea geoArea) {
        if (geoArea != null) {
            this.motherArea = geoArea;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Standard getter method, to return the Geographical Area that contains the current Geographic Area.
     *
     * @return is the Geographical Area that contains this Geographical Area.
     */
    GeographicArea getMotherArea() {
        return this.motherArea;
    }


    /**
     * This method is used to check if the current GeoArea is contained in the given area.
     *
     * @param motherArea - Geographic Area being compared to daughter area mother area attribute.
     * @return true if contained, false if not.
     */

    public boolean isContainedInArea(GeographicArea motherArea) {
        GeographicArea tempMotherArea = this;
        while (tempMotherArea.getMotherArea() != null) {
            if (tempMotherArea.isMotherAreaEqual(motherArea)) {
                return true;
            } else {
                tempMotherArea = this.getMotherArea();
            }
        }
        return false;
    }

    /**
     * Getter for type of Geographic Area.
     *
     * @return returns the attribute AreaType from an object of the class Geographic Area
     */
    public AreaType getAreaType() {
        return this.areaType;
    }


    /**
     * Getter for Geographic Area localization.
     *
     * @return returns the attribute local from an object of the class Geographic Area
     */
    public Local getLocal() {
        return this.location;
    }

    /**
     * Getter for Geographic Area sensor list.
     *
     * @return returns the attribute sensorList from an object of the class Geographic Area
     */
    public AreaSensorService getSensorList() {
        return this.areaSensors;
    }

    /**
     * Method to print details that are required for a Geographic Area to be different from another GA (equals -
     * name, type area and local).
     *
     * @return returns a string with Geographic Area Parameters
     */

    public String buildString() {
        String result;
        result = this.name + ", " + this.areaType.getName() + ", " +
                this.location.getLatitude() + "º lat, " + this.location.getLongitude() + "º long\n";
        return result;
    }

    public double getWidth() {
        return this.width;
    }

    public double getLength() {
        return this.length;
    }

    /**
     * Method adds sensor to geographic area sensor list.
     *
     * @param areaSensor to addWithoutPersisting
     * @return true in case the sensor is added, false otherwise.
     **/
    public boolean addSensor(AreaSensor areaSensor) {
        return this.areaSensors.add(areaSensor);
    }

    /**
     * Method checks if geographic area's sensor list is empty.
     *
     * @return true if AreaSensorList is empty, false otherwise
     */
    public boolean isSensorListEmpty() {
        return areaSensors.isEmpty();
    }

    /**
     * This method checks if mother area is equal to geographic area given.
     *
     * @param geographicArea the GA to be tested.
     * @return true if is equal to geographic area given, false otherwise.
     **/

    private boolean isMotherAreaEqual(GeographicArea geographicArea) {
        return this.motherArea.equals(geographicArea);
    }

    /**
     * This method checks if name, type area and local match those of geographic area.
     *
     * @param name     the name of the Geographic Area
     * @param local    the localization on the Geographic Area
     * @param areaType the type of Geographic Area
     * @return true if it matches, false if it does not.
     **/

    boolean equalsParameters(String name, AreaType areaType, Local local) {
        return (this.name.equals(name) && (this.areaType.equals(areaType) && (this.location.equals(local))));
    }

    /**
     * This method checks if type area given match that of geographic area.
     *
     * @param areaType the type of Area
     * @return true if it matches, false if it does not.
     **/

    boolean equalsTypeArea(AreaType areaType) {
        return (this.areaType.equals(areaType));
    }

    /**
     * This method receives the string type name and returns a list of sensors
     * from geographic area of that type.
     *
     * @param type the type of Sensor to test.
     * @return AreaSensorList of given type
     **/

    AreaSensorService getSensorsOfGivenType(String type) {
        return this.areaSensors.getSensorListByType(type);
    }


    /**
     * Method 'equals' is required so that each 'Geographic Area' can be added to a 'Geographic Area List'. Two
     * Geographic Areas cannot have the same Localization, name and AreaType
     */
    @Override
    public boolean equals(Object testObject) {
        if (this == testObject) {
            return true;
        }
        if (!(testObject instanceof GeographicArea)) {
            return false;
        }
        GeographicArea gA = (GeographicArea) testObject;
        return (this.getLocal().equals(gA.getLocal()) && (this.getName().equals(gA.getName()) && (this.getAreaType().equals(gA.getAreaType()))));
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
