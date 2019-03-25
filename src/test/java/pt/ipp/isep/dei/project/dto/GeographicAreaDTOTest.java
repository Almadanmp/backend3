package pt.ipp.isep.dei.project.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class GeographicAreaDTOTest {

    @Test
    void seeIfEqualsWorks() {
        //Arrange
        SensorDTO sensorDTO1 = new SensorDTO();

        List<SensorDTO> dtoList = new ArrayList<>();
        dtoList.add(sensorDTO1);
        List<SensorDTO> dtoListEmpty = new ArrayList<>();

        GeographicAreaDTO geographicAreaDTO1 = new GeographicAreaDTO();
        GeographicAreaDTO geographicAreaDTO2 = new GeographicAreaDTO();
        GeographicAreaDTO geographicAreaDTO3 = new GeographicAreaDTO();
        GeographicAreaDTO geographicAreaDTO4 = new GeographicAreaDTO();
        GeographicAreaDTO geographicAreaDTO5 = new GeographicAreaDTO();
        GeographicAreaDTO geographicAreaDTO6 = new GeographicAreaDTO();
        GeographicAreaDTO geographicAreaDTO7 = new GeographicAreaDTO();
        GeographicAreaDTO geographicAreaDTO8 = new GeographicAreaDTO();
        GeographicAreaDTO geographicAreaDTO9 = new GeographicAreaDTO();

        geographicAreaDTO1.setDescription("geoAreaDTO1");
        geographicAreaDTO2.setDescription("geoAreaDTO2");
        geographicAreaDTO3.setDescription("geoAreaDTO3");
        geographicAreaDTO4.setDescription("geoAreaDTO4");
        geographicAreaDTO5.setDescription("geoAreaDTO5");
        geographicAreaDTO6.setDescription("geoAreaDTO1");
        geographicAreaDTO7.setDescription("geoAreaDTO1");
        geographicAreaDTO8.setDescription("geoAreaDTO1");
        geographicAreaDTO9.setDescription("geoAreaDTO1");

        geographicAreaDTO1.setName("01");
        geographicAreaDTO2.setName("02");
        geographicAreaDTO3.setName("03");
        geographicAreaDTO4.setName("01");
        geographicAreaDTO5.setName("01");
        geographicAreaDTO6.setName("01");
        geographicAreaDTO7.setName("01");
        geographicAreaDTO8.setName("01");
        geographicAreaDTO9.setName("01");

        geographicAreaDTO1.setAltitude(1);
        geographicAreaDTO2.setAltitude(2);
        geographicAreaDTO3.setAltitude(1);
        geographicAreaDTO4.setAltitude(1);
        geographicAreaDTO5.setAltitude(4);
        geographicAreaDTO6.setAltitude(1);
        geographicAreaDTO7.setAltitude(1);
        geographicAreaDTO8.setAltitude(1);
        geographicAreaDTO9.setAltitude(1);

        geographicAreaDTO1.setLongitude(1);
        geographicAreaDTO2.setLongitude(2);
        geographicAreaDTO3.setLongitude(1);
        geographicAreaDTO4.setLongitude(1);
        geographicAreaDTO5.setLongitude(1);
        geographicAreaDTO6.setLongitude(1);
        geographicAreaDTO7.setLongitude(5);
        geographicAreaDTO8.setLongitude(1);
        geographicAreaDTO9.setLongitude(1);

        geographicAreaDTO1.setLatitude(1);
        geographicAreaDTO2.setLatitude(2);
        geographicAreaDTO3.setLatitude(1);
        geographicAreaDTO4.setLatitude(1);
        geographicAreaDTO5.setLatitude(1);
        geographicAreaDTO6.setLatitude(1);
        geographicAreaDTO7.setLatitude(1);
        geographicAreaDTO8.setLatitude(5);
        geographicAreaDTO9.setLatitude(1);

        geographicAreaDTO1.setTypeArea("City");
        geographicAreaDTO2.setTypeArea("Town");
        geographicAreaDTO3.setTypeArea("City");
        geographicAreaDTO4.setTypeArea("Country");
        geographicAreaDTO5.setTypeArea("City");
        geographicAreaDTO6.setTypeArea("City");
        geographicAreaDTO7.setTypeArea("City");
        geographicAreaDTO8.setTypeArea("City");
        geographicAreaDTO9.setTypeArea("City");

        geographicAreaDTO1.setSensorDTOList(dtoList);
        geographicAreaDTO2.setSensorDTOList(dtoListEmpty);
        geographicAreaDTO3.setSensorDTOList(dtoList);
        geographicAreaDTO4.setSensorDTOList(dtoList);
        geographicAreaDTO5.setSensorDTOList(dtoList);
        geographicAreaDTO6.setSensorDTOList(dtoList);
        geographicAreaDTO7.setSensorDTOList(dtoList);
        geographicAreaDTO8.setSensorDTOList(dtoList);
        geographicAreaDTO9.setSensorDTOList(dtoListEmpty);

        //Assert

        assertEquals(geographicAreaDTO1, geographicAreaDTO1); // same object
        assertEquals(geographicAreaDTO1, geographicAreaDTO6); // different object
        assertNotEquals(geographicAreaDTO1, geographicAreaDTO2); //everything different
        assertNotEquals(geographicAreaDTO1, geographicAreaDTO3); // id different
        assertNotEquals(geographicAreaDTO1, geographicAreaDTO4); // type area
        assertEquals(geographicAreaDTO1, geographicAreaDTO5); //altitude
        assertNotEquals(geographicAreaDTO1, geographicAreaDTO7); //longitude
        assertNotEquals(geographicAreaDTO1, geographicAreaDTO8); //latitude
        assertNotEquals(geographicAreaDTO1, geographicAreaDTO9); //list
        assertNotEquals(geographicAreaDTO1, 3D);
    }

    @Test
    void seeIfHashCodeWorks() {
        //Arrange

        GeographicAreaDTO geographicAreaDTO = new GeographicAreaDTO();

        //Act

        int actualResult = geographicAreaDTO.hashCode();

        //Assert

        assertEquals(1, actualResult);
    }
}