
import java.util.ArrayList;
import java.util.List;

interface Capability {
    String getName();
    boolean apply(String value);
}

class PowerCapability implements Capability {
    private boolean on = false;

    @Override
    public String getName() {
        return "Power";
    }

    @Override
    public boolean apply(String value) {
        if (value.equalsIgnoreCase("ON")) {
            on = true;
        } else if (value.equalsIgnoreCase("OFF")) {
            on = false;
        } else {
            return false;
        }
        return true;
    }

    public boolean isOn() {
        return on;
    }
}

class BrightnessCapability implements Capability {
    private int brightness = 0;

    @Override
    public String getName() {
        return "Brightness";
    }

    @Override
    public boolean apply(String value) {
        try {
            int level = Integer.parseInt(value);

            if (level < 0 || level > 100) {
                return false;
            }

            brightness = level;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int getBrightness() {
        return brightness;
    }
}

class TemperatureCapability implements Capability {
    private int temperature = 16;

    @Override
    public String getName() {
        return "Temperature";
    }

    @Override
    public boolean apply(String value) {
        try {
            int level = Integer.parseInt(value);

            if (level < 16 || level > 30) {
                return false;
            }

            temperature = level;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int getTemperature() {
        return temperature;
    }
}

class Device {
    private String deviceName;
    private List<Capability> capabilities;

    public Device(String deviceName) {
        this.deviceName = deviceName;
        capabilities = new ArrayList<>();
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void addCapability(Capability capability) {
        if (findCapability(capability.getName()) == null) {
            capabilities.add(capability);
            System.out.println(deviceName + ": "
                    + capability.getName() + " capability added.");
        }
    }

    public Capability findCapability(String name) {
        for (Capability capability : capabilities) {
            if (capability.getName().equalsIgnoreCase(name)) {
                return capability;
            }
        }
        return null;
    }

    public boolean applyCapability(String name, String value) {
        Capability capability = findCapability(name);

        if (capability == null) {
            return false;
        }

        return capability.apply(value);
    }
}

class SceneStep {
    private String capabilityName;
    private String value;

    public SceneStep(String capabilityName, String value) {
        this.capabilityName = capabilityName;
        this.value = value;
    }

    public String getCapabilityName() {
        return capabilityName;
    }

    public String getValue() {
        return value;
    }
}

class Scene {
    private String sceneName;
    private List<SceneStep> steps;

    public Scene(String sceneName) {
        this.sceneName = sceneName;
        steps = new ArrayList<>();
    }

    public void addStep(SceneStep step) {
        steps.add(step);
    }

    public void execute(List<Device> devices) {
        System.out.println("Scene '" + sceneName + "' started.");

        int actions = 0;

        for (SceneStep step : steps) {
            for (Device device : devices) {
                Capability capability =
                        device.findCapability(step.getCapabilityName());

                if (capability != null) {
                    boolean success = capability.apply(step.getValue());

                    if (success) {
                        actions++;

                        String name = step.getCapabilityName();

                        if (name.equalsIgnoreCase("Power")) {
                            System.out.println(device.getDeviceName()
                                    + ": " + step.getValue() + ".");
                        } else if (name.equalsIgnoreCase("Brightness")) {
                            System.out.println(device.getDeviceName()
                                    + ": brightness set to "
                                    + step.getValue() + "%.");
                        } else if (name.equalsIgnoreCase("Temperature")) {
                            System.out.println(device.getDeviceName()
                                    + ": temperature set to "
                                    + step.getValue() + "°C.");
                        }
                    }
                }
            }
        }

        System.out.println("Scene '" + sceneName
                + "' completed: " + actions + " actions applied.");
    }
}

public class Pb3 {
    public static void main(String[] args) {

        Device ac = new Device("Lab AC");
        ac.addCapability(new PowerCapability());
        ac.addCapability(new TemperatureCapability());

        Device lights = new Device("Ceiling Lights");
        lights.addCapability(new PowerCapability());
        lights.addCapability(new BrightnessCapability());

        Device projector = new Device("Projector");
        projector.addCapability(new PowerCapability());

        List<Device> devices = new ArrayList<>();
        devices.add(ac);
        devices.add(lights);
        devices.add(projector);

        Scene lectureMode = new Scene("Lecture Mode");

        lectureMode.addStep(new SceneStep("Power", "ON"));
        lectureMode.addStep(new SceneStep("Brightness", "40"));
        lectureMode.addStep(new SceneStep("Temperature", "24"));

        lectureMode.execute(devices);

        System.out.println();

        boolean result = ac.applyCapability("Temperature", "12");

        if (!result) {
            System.out.println(
                    "Rejected: Lab AC temperature must be between 16°C and 30°C.");
        }

        System.out.println();

        projector.addCapability(new BrightnessCapability());

        projector.applyCapability("Brightness", "70");

        System.out.println("Projector: brightness set to 70%.");
    }
}
