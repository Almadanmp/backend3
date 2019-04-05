package pt.ipp.isep.dei.project.reader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pt.ipp.isep.dei.project.controller.ReaderController;
import pt.ipp.isep.dei.project.model.GeographicArea;
import pt.ipp.isep.dei.project.model.GeographicAreaList;
import pt.ipp.isep.dei.project.model.Local;
import pt.ipp.isep.dei.project.model.TypeArea;
import pt.ipp.isep.dei.project.model.sensor.Sensor;
import pt.ipp.isep.dei.project.model.sensor.SensorList;
import pt.ipp.isep.dei.project.model.sensor.TypeSensor;
import pt.ipp.isep.dei.project.services.SensorService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReaderXMLGeoArea {

    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String ALTITUDE = "altitude";
    private static final String VALID_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * reads a XML file from a certain path and imports geographic areas and sensors from the file
     *
     * @param filePath path to the xml file
     * @param list     geographic area list to addWithoutPersisting the imported geographic areas
     */
    public int readFileXMLAndAddAreas(String filePath, GeographicAreaList list, SensorService service) {
        ReaderController ctrl = new ReaderController(service);
        ReaderXML reader = new ReaderXML();
        Document doc = reader.readFile(filePath);
        doc.getDocumentElement().normalize();
        NodeList nListGeoArea = doc.getElementsByTagName("geographical_area");
        return ctrl.addGeoAreaNodeListToList(nListGeoArea,list);
    }

    /**
     * Method to import a Geographic Area from a certain node
     *
     * @param node - node of the XML file
     * @return - Geographic Area that exists in the node
     */
    public GeographicArea readGeographicAreasXML(Node node) {
        GeographicArea geoArea = new GeographicArea();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String description = getTagValue("description", element);
            String id = getTagValue("id", element);
            double length = Double.parseDouble(getTagValue("length", element));
            double width = Double.parseDouble(getTagValue("width", element));
            Local local = new Local(Double.parseDouble(getTagValue(LATITUDE, element)),
                    Double.parseDouble(getTagValue(LONGITUDE, element)),
                    Double.parseDouble(getTagValue(ALTITUDE, element)));
            TypeArea typeArea = new TypeArea(getTagValue("type", element));
            geoArea = new GeographicArea(id, typeArea, length, width, local);
            geoArea.setDescription(description);
            NodeList nListSensor = element.getElementsByTagName("sensor");
            SensorList sensorList = new SensorList();
            for (int j = 0; j < nListSensor.getLength(); j++) {
                sensorList.add(readSensorsXML(nListSensor.item(j), sensorList));
            }
            geoArea.setSensorList(sensorList);


        }
        return geoArea;
    }

    /**
     * Method to import a Sensor from a certain node
     *
     * @param node - node of the XML file.
     * @return - Sensor that exists in the node
     */
    private Sensor readSensorsXML(Node node, SensorList sensorList) {
        Sensor sensor = new Sensor();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String id = getTagValue("id", element);
            String name = getTagValue("name", element);
            String sensorDate = getTagValue("start_date", element);
            TypeSensor typeSensor = new TypeSensor(getTagValue("type", element), getTagValue("units", element));
            SimpleDateFormat validDateFormat = new SimpleDateFormat(VALID_DATE_FORMAT);
            Local local = new Local(Double.parseDouble(getTagValue(LATITUDE, element)),
                    Double.parseDouble(getTagValue(LONGITUDE, element)),
                    Double.parseDouble(getTagValue(ALTITUDE, element)));
            Date date = new Date();
            try {
                date = validDateFormat.parse(sensorDate);
            } catch (ParseException expected) {
                expected.getErrorOffset();
            }
            sensor = new Sensor(id, name, typeSensor, local, date);
            sensor.setSensorList(sensorList);
        }
        return sensor;
    }

    /**
     * Gets the value of the tag correspondent to the String and the Element from the same Node
     *
     * @param tag     - String of the tag correspondent to the node
     * @param element - element correspondent to the nod
     * @return - returns the value in string
     */
    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

}
