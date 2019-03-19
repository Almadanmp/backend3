package pt.ipp.isep.dei.project.io.ui;

import pt.ipp.isep.dei.project.controller.GASettingsController;
import pt.ipp.isep.dei.project.dto.LocalDTO;
import pt.ipp.isep.dei.project.io.ui.utils.InputUtils;
import pt.ipp.isep.dei.project.io.ui.utils.UtilsUI;
import pt.ipp.isep.dei.project.model.*;

import java.util.Scanner;

class GASettingsUI {
    private GASettingsController controller;

    GASettingsUI() {
        this.controller = new GASettingsController();
    }

    void runGASettings(GeographicAreaList programGAList, TypeAreaList programTypeAreaList) {
        boolean activeInput = true;
        int option;
        System.out.println("--------------\nGeographic Area Settings\n--------------\n");
        while (activeInput) {
            printOptionMessage();
            option = InputUtils.getInputAsInt();
            switch (option) {
                case 1:
                    runUS01(programTypeAreaList);
                    activeInput = false;
                    break;
                case 2:
                    runUS02(programTypeAreaList);
                    activeInput = false;
                    break;
                case 3:
                    runUS03(programGAList, programTypeAreaList);
                    activeInput = false;
                    break;
                case 4:
                    runUS04(programGAList, programTypeAreaList);
                    activeInput = false;
                    break;
                case 5:
                    runUS07(programGAList);
                    activeInput = false;
                    break;
                case 6:
                    runUS08(programGAList);
                    activeInput = false;
                    break;
                case 7:
                    runUS10(programGAList);
                    activeInput = false;
                    break;
                case 0:
                    return;
                default:
                    System.out.println(UtilsUI.INVALID_OPTION);
                    break;
            }
        }
    }


    // SHARED METHODS //

    private TypeArea getInputTypeAreaByList(TypeAreaList typeAreaList) {
        while (true) {
            System.out.println("Please select the Geographic Area Type from the list: ");
            System.out.print(controller.buildGATypeListString(typeAreaList));
            int aux = InputUtils.getInputAsInt();
            if (aux >= 0 && aux < typeAreaList.size()) {
                TypeArea typeArea = typeAreaList.get(aux);
                System.out.println("You have chosen the following Geographic Area Type:");
                System.out.println("TypeArea: " + controller.getTypeAreaName(typeArea));
                return typeArea;
            } else {
                System.out.println(UtilsUI.INVALID_OPTION);
            }
        }
    }


    /* USER STORY 001 - As an Administrator, I want to add a new type of geographical area, in order to be able to create a
     classification of geographical areas.*/
    private void runUS01(TypeAreaList typeAreaList) {
        if (typeAreaList == null) {
            System.out.println(UtilsUI.INVALID_GA_TYPE_LIST);
            return;
        }
        String typeAreaName = getInputUS01();
        boolean created = updateModelUS01(typeAreaList, typeAreaName);
        displayStateUS01(created);
    }

    private String getInputUS01() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please insert the name of the new Geographic Area Type: ");
        while (!scanner.hasNext("[a-zA-Z_]+")) {
            System.out.println("That's not a valid name a Type Area. Please insert only Alphabetic Characters");
            scanner.next();
        }
        return scanner.next();
    }

    private boolean updateModelUS01(TypeAreaList typeAreaList, String typeAreaName) {
        return controller.createAndAddTypeAreaToList(typeAreaName, typeAreaList);
    }

    private void displayStateUS01(boolean created) {
        if (created) {
            System.out.println("Success, you have inserted a new Type of Geographic Area.");
        } else {
            System.out.println("Failed, you have inserted an invalid or repeated Type of Geographic Area.");
        }

    }

    /* USER STORY 002 - As a System Administrator I want to obtain a list of the types of Geographical Areas previously stated.
     * Class responsible for presenting the list. - NUNO AZEVEDO */
    private void runUS02(TypeAreaList typeAreaList) {
        updateAndDisplayUS02(typeAreaList);
    }

    private void updateAndDisplayUS02(TypeAreaList typeAreaList) {
        if (!typeAreaList.isEmpty()) {
            System.out.println(controller.getTypeAreaList(typeAreaList));
            System.out.println("\nList finished.");
        } else {
            System.out.println(UtilsUI.INVALID_GA_TYPE_LIST);
        }
    }

    /* User Story - 03 As a System Administrator I want to create a new Geographic Area */
    private void runUS03(GeographicAreaList geographicAreaList, TypeAreaList typeAreaList) {
        if (typeAreaList.isEmpty()) {
            System.out.println(UtilsUI.INVALID_GA_TYPE_LIST);
            return;
        }
        boolean created = getAreaInputUS03(geographicAreaList, typeAreaList);
        generateResultUS03(created);
    }

    private boolean getAreaInputUS03(GeographicAreaList geographicAreaList, TypeAreaList typeAreaList) {
        Scanner scanner = new Scanner(System.in);
        TypeArea geoTypeArea = getInputTypeAreaByList(typeAreaList);
        String gaTypeAreaName = controller.getTypeAreaName(geoTypeArea);
        String nameOfGeoArea = readInputString("name");
        double geoAreaLat = readInputNumber("Latitude");
        double geoAreaLong = readInputNumber("Longitude");
        double geoAreaAlt = readInputNumber("Altitude");
        double geoAreaLength = readInputPositiveNumber("Length");
        double geoAreaWidth = readInputPositiveNumber("Width");
        String geoAreDescription = null;
        if (InputUtils.yesOrNo("Would you like to add a description to the new geographic area? (y/n)")) {
            System.out.println("Please insert the geographic area description:");
            geoAreDescription = scanner.nextLine();
        }
        System.out.print("The Geographic Area you want to create is " + nameOfGeoArea + " from the type " + gaTypeAreaName +
                " and its " + "localization is on " + geoAreaLat + " latitude " + geoAreaLong + " longitude. The geographic area size" +
                " is " + geoAreaLength + " by " + geoAreaWidth + " kms\n");
        if (geoAreDescription != null) {
            System.out.println("And has the following description: " + geoAreDescription);
        }
        LocalDTO localDTO = controller.createLocal(geoAreaLat, geoAreaLong, geoAreaAlt);
        return controller.addNewGeoAreaToList(geographicAreaList, nameOfGeoArea, geoTypeArea, localDTO, geoAreaLength, geoAreaWidth);
    }

    private void generateResultUS03(boolean created) {
        if (created) {
            System.out.print("The Geographic Area has been successfully added.");
        } else
            System.out.print("The Geographic Area hasn't been added to the list. " +
                    "There is already an area with those input values.");
    }

    private String createInputMsg(String inputType) {
        return "Please Insert " + inputType + " for the New Geographic Area: ";
    }

    private String createInvalidStringMsg(String inputType) {
        return "That's not a valid " + inputType + ". Please insert only Alphabetic Characters";
    }

    private String readInputString(String inputType) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(createInputMsg(inputType));

        while (!scanner.hasNext("[a-zA-Z\\sà-ùÀ-Ù]*")) {
            System.out.println(createInvalidStringMsg(inputType));
            scanner.next();
        }
        return scanner.next();
    }

    private double readInputNumber(String inputType) {
        System.out.print(createInputMsg(inputType));
        return InputUtils.getInputAsDouble();
    }

    private double readInputPositiveNumber(String inputType) {
        System.out.print(createInputMsg(inputType));
        return InputUtils.getInputAsDoublePositive();
    }

    /* USER STORY 04 -  As an Administrator, I want to get a list of existing geographical areas of a given type. */
    private void runUS04(GeographicAreaList geographicAreaList, TypeAreaList typeAreaList) {
        if (typeAreaList.isEmpty()) {
            System.out.println(UtilsUI.INVALID_GA_TYPE_LIST);
            return;
        }
        if (geographicAreaList.isEmpty()) {
            System.out.println(UtilsUI.INVALID_GA_LIST);
            return;
        }
        TypeArea typeArea = getInputTypeAreaByList(typeAreaList);
        GeographicAreaList gaFinalList = matchGAByTypeArea(geographicAreaList, typeArea);
        displayGAListByTypeArea(gaFinalList, typeArea);
    }

    private GeographicAreaList matchGAByTypeArea(GeographicAreaList geographicAreaList, TypeArea typeArea) {
        return controller.matchGAByTypeArea(geographicAreaList, typeArea);
    }

    private void displayGAListByTypeArea(GeographicAreaList gaFinalList, TypeArea typeArea) {
        String taName = controller.getTypeAreaName(typeArea);
        System.out.println("Geographic Areas of the type " + taName + ":\n");
        System.out.println(controller.buildGAListString(gaFinalList));
    }

    /* USER STORY 07 -  Add an existing geographical area to another one. */
    private void runUS07(GeographicAreaList geographicAreaList) {
        if (geographicAreaList.isEmpty()) {
            System.out.println(UtilsUI.INVALID_GA_LIST);
            return;
        }
        GeographicArea motherGA = getInputMotherGeographicArea(geographicAreaList);
        GeographicArea daughterGA = getInputDaughterGeographicArea(geographicAreaList);
        updateStateUS07(motherGA, daughterGA);
        displayStateUS07(motherGA, daughterGA);
    }

    private GeographicArea getInputMotherGeographicArea(GeographicAreaList programGAList) {
        System.out.println("First you need to select the geographic area you wish to set as container.");
        return InputUtils.getGeographicAreaByList(programGAList);
    }

    private GeographicArea getInputDaughterGeographicArea(GeographicAreaList programGAList) {
        System.out.println("Second you need to select the geographic area you wish to set as contained.");
        return InputUtils.getGeographicAreaByList(programGAList);
    }

    private void updateStateUS07(GeographicArea motherGA, GeographicArea daughterGA) {
        controller.setMotherArea(daughterGA, motherGA);
    }

    private void displayStateUS07(GeographicArea motherGA, GeographicArea daughterGA) {
        String motherGAName = controller.getGeographicAreaId(motherGA);
        String daughterGAName = controller.getGeographicAreaId(daughterGA);
        System.out.print("The Geographic Area " + daughterGAName + " is contained in " + motherGAName + ".");
    }


    /* US08 - As an Administrator, I want to find out if a geographical area is included, directly
    or indirectly, in another one. */
    private void runUS08(GeographicAreaList geographicAreaList) {
        if (geographicAreaList.isEmpty()) {
            System.out.println(UtilsUI.INVALID_GA_LIST);
            return;
        }
        GeographicArea motherGA = getMotherArea(geographicAreaList);
        GeographicArea daughterGA = getDaughterArea(geographicAreaList);
        checkIfContained(motherGA, daughterGA);
    }

    /**
     * getInputGeographicContainer()
     * this method makes the user define the NAME of the GeographicArea CONTAINER
     */
    private GeographicArea getMotherArea(GeographicAreaList geographicAreaList) {
        System.out.println("First you need to select the geographic area you wish to test if contains another geographic area.");
        return InputUtils.getGeographicAreaByList(geographicAreaList);
    }

    /**
     * getInputGeographicContainer()
     * this method makes the user define the NAME of the GeographicArea CONTAINED
     */
    private GeographicArea getDaughterArea(GeographicAreaList geographicAreaList) {
        System.out.println("Second you need to select the geographic area you wish to test if is contained in the first one.");
        return InputUtils.getGeographicAreaByList(geographicAreaList);
    }

    /**
     * This method receives two geographic areas and checks if the first geographic area
     * contains the second geographic area, returning a message to the user accordingly.
     */
    private void checkIfContained(GeographicArea motherGA, GeographicArea daughterGA) {
        if (!(controller.isAreaContained(motherGA, daughterGA))) {
            System.out.println(controller.getGeographicAreaId(daughterGA) + " is NOT contained in " + controller.getGeographicAreaId(motherGA));
        } else {
            System.out.println(controller.getGeographicAreaId(daughterGA) + " is contained in " + controller.getGeographicAreaId(motherGA));
        }
    }

    /**
     * This method activates or deactivates a sensor selected from a list of sensor of an selected geographic area
     * @param geographicAreaList geographic area list
     */
    private void runUS10(GeographicAreaList geographicAreaList) {
        GeographicArea geographicArea = InputUtils.getGeographicAreaByList(geographicAreaList);
        if (geographicArea.isSensorListEmpty()) {
            UtilsUI.printMessage(UtilsUI.INVALID_SENSOR_LIST);
            return;
        }
        Sensor sensor = InputUtils.getInputSensorByList(geographicArea.getSensorList());
        controller.activateOrDeactivateSensor(sensor);
        updateStateUS10(sensor);
    }

    private void updateStateUS10(Sensor sensor) {
        if (sensor.isActive()) {
            System.out.println("Sensor successfully activated!");
        } else {
            System.out.println("Sensor successfully deactivated!");
        }
    }

    /* UI SPECIFIC METHODS - NOT USED ON USER STORIES */
    private void printOptionMessage() {
        System.out.println("Geographic Settings Options:\n");
        System.out.println("1) Create a new type of Geographical Area. (US001)");
        System.out.println("2) List the existing types of Geographical Areas. (US002)");
        System.out.println("3) Add a new geographical area. (US003)");
        System.out.println("4) List of existing geographical areas of a given type. (US004)");
        System.out.println("5) Add an existing geographical area to another one. (US007)");
        System.out.println("6) See if a geographical area is included, directly or indirectly, in another one. (US008)");
        System.out.println("7) Deactivate or activate a sensor (US010) \n");
        System.out.println("0) (Return to main menu)\n");
    }
}
