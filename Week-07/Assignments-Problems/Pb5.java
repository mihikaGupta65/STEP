
abstract class HomeDevice {
    private static int serialCounter = 1000;
    private final String serialNumber;

    public HomeDevice() {
        serialCounter++;
        serialNumber = "HD-" + serialCounter;
    }

    public abstract String activate();

    public String getSerialNumber() {
        return serialNumber;
    }
}

interface RemoteControllable {
    String connect(String appId);
}

interface EnergyTrackable {
    double getConsumptionWatts();
}

class WashingMachine extends HomeDevice implements RemoteControllable, EnergyTrackable {
    private double consumptionWatts;

    public WashingMachine(double consumptionWatts) {
        super();
        this.consumptionWatts = consumptionWatts;
    }

    @Override
    public String activate() {
        return "Washing machine " + getSerialNumber()
                + " started a cycle";
    }

    @Override
    public String connect(String appId) {
        return getSerialNumber() + " connected to " + appId;
    }

    @Override
    public double getConsumptionWatts() {
        return consumptionWatts;
    }
}

class Refrigerator extends HomeDevice implements EnergyTrackable {
    private double consumptionWatts;

    public Refrigerator(double consumptionWatts) {
        super();
        this.consumptionWatts = consumptionWatts;
    }

    @Override
    public String activate() {
        return "Refrigerator " + getSerialNumber()
                + " started cooling";
    }

    @Override
    public double getConsumptionWatts() {
        return consumptionWatts;
    }
}

class MobileApp implements RemoteControllable {
    private String appName;

    public MobileApp(String appName) {
        this.appName = appName;
    }

    @Override
    public String connect(String appId) {
        return appName + " connected to " + appId;
    }
}

public class Pb5 {

    static void connectAll(RemoteControllable[] items, String appId) {
        for (RemoteControllable item : items) {
            System.out.println(item.connect(appId));
        }
    }

    static double getConsumptionIfTrackable(HomeDevice d) {
        if (d instanceof EnergyTrackable) {
            EnergyTrackable item = (EnergyTrackable) d;
            return item.getConsumptionWatts();
        }

        return 0.0;
    }

    public static void main(String[] args) {
        WashingMachine wm = new WashingMachine(500.0);

        System.out.println(wm.activate());
        System.out.println(wm.connect("HomeConnect"));

        System.out.println();

        Refrigerator fridge = new Refrigerator(150.0);

        System.out.println("Refrigerator Consumption: "
                + getConsumptionIfTrackable(fridge));

        System.out.println();

        MobileApp app = new MobileApp("HomeConnect App");

        System.out.println(app.connect("HomeConnect"));

        System.out.println();

        HomeDevice ref = wm;

        System.out.println("Washing Machine Consumption: "
                + getConsumptionIfTrackable(ref));

        System.out.println();

        RemoteControllable[] items = {wm, app};

        connectAll(items, "HomeConnect");
    }
}
