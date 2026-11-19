
interface Dimmable {
    void dim(int level);
}

interface Schedulable {
    void schedule(String time);
}

interface EnergyMonitor {
    void showEnergy();
}

class Device {
    protected String id;
    protected boolean isOn;

    public Device(String id) {
        this.id = id;
        this.isOn = false;
    }

    public void turnOn() {
        isOn = true;
        System.out.println(id + " is ON");
    }

    public void turnOff() {
        isOn = false;
        System.out.println(id + " is OFF");
    }

    public String getId() {
        return id;
    }
}

class Light extends Device implements Dimmable, Schedulable {
    public Light(String id) {
        super(id);
    }

    public void dim(int level) {
        if (level >= 0 && level <= 100) {
            System.out.println(id + " dimmed to " + level);
        } else {
            System.out.println("Invalid dim level");
        }
    }

    public void schedule(String time) {
        System.out.println(id + " scheduled " + time);
    }
}

class Fan extends Device implements Schedulable {
    public Fan(String id) {
        super(id);
    }

    public void schedule(String time) {
        System.out.println(id + " scheduled " + time);
    }
}

class Plug extends Device implements EnergyMonitor {
    private double energy;

    public Plug(String id, double energy) {
        super(id);
        this.energy = energy;
    }

    public void showEnergy() {
        System.out.println(id + " energy " + energy + " kWh");
    }
}

public class Pb1 {

    public static void main(String[] args) {
        Device[] devices = {
            new Light("L1"),
            new Fan("F1"),
            new Plug("P1", 12)
        };

        commandOn(devices, "L1");
        commandDim(devices, "L1", 40);
        commandDim(devices, "F1", 30);
        commandSchedule(devices, "F1", "22:00");
        commandEnergy(devices, "P1");
        commandEnergy(devices, "L1");
    }

    static Device findDevice(Device[] devices, String id) {
        for (Device device : devices) {
            if (device.getId().equals(id)) {
                return device;
            }
        }
        return null;
    }

    static void commandOn(Device[] devices, String id) {
        Device device = findDevice(devices, id);

        if (device != null) {
            device.turnOn();
        }
    }

    static void commandDim(Device[] devices, String id, int level) {
        Device device = findDevice(devices, id);

        if (device instanceof Dimmable) {
            ((Dimmable) device).dim(level);
        } else {
            System.out.println(id + " rejected: DIM unsupported");
        }
    }

    static void commandSchedule(Device[] devices, String id, String time) {
        Device device = findDevice(devices, id);

        if (device instanceof Schedulable) {
            ((Schedulable) device).schedule(time);
        } else {
            System.out.println(id + " rejected: SCHEDULE unsupported");
        }
    }

    static void commandEnergy(Device[] devices, String id) {
        Device device = findDevice(devices, id);

        if (device instanceof EnergyMonitor) {
            ((EnergyMonitor) device).showEnergy();
        } else {
            System.out.println(id + " rejected: ENERGY unsupported");
        }
    }
}
