package pt.ipp.isep.dei.project.model.device.devicetypes;

import pt.ipp.isep.dei.project.model.device.DeviceTemporary;
import pt.ipp.isep.dei.project.model.device.devicespecs.DeviceSpecs;
import pt.ipp.isep.dei.project.model.device.devicespecs.WashingMachineSpec;

public class WashingMachineDT implements DeviceType {

    public DeviceTemporary createDeviceType() {
        DeviceSpecs ds = new WashingMachineSpec();
        return new DeviceTemporary(ds);
    }

    public String getDeviceType() {
        return "Washing Machine";
    }
}
