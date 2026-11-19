
abstract class LibraryItem {
    private static int itemCounter = 1000;
    private final String itemId;

    public LibraryItem() {
        itemCounter++;
        itemId = "LIB-" + itemCounter;
    }

    public abstract int getLoanPeriodDays();

    public String getItemId() {
        return itemId;
    }
}

interface Renewable {
    String renew();
}

interface Reservable {
    String reserve();
}

class Textbook extends LibraryItem implements Renewable, Reservable {
    private String title;

    public Textbook(String title) {
        super();
        this.title = title;
    }

    @Override
    public int getLoanPeriodDays() {
        return 14;
    }

    @Override
    public String renew() {
        return title + " renewed";
    }

    @Override
    public String reserve() {
        return title + " reserved";
    }
}

class Magazine extends LibraryItem implements Renewable {
    private String title;

    public Magazine(String title) {
        super();
        this.title = title;
    }

    @Override
    public int getLoanPeriodDays() {
        return 7;
    }

    @Override
    public String renew() {
        return title + " renewed";
    }
}

class DigitalPass implements Renewable {
    private String resourceName;

    public DigitalPass(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String renew() {
        return resourceName + " renewed";
    }
}

public class Pb5 {

    static void processCheckouts(LibraryItem[] items) {
        for (LibraryItem item : items) {
            System.out.println(
                    item.getItemId() + " | Loan Period: "
                    + item.getLoanPeriodDays() + " days");
        }
    }

    static String reserveIfSupported(Object o) {
        if (o instanceof Reservable) {
            Reservable item = (Reservable) o;
            return item.reserve();
        }

        return "Reservation not supported";
    }

    public static void main(String[] args) {
        Textbook t = new Textbook("Java Fundamentals");

        System.out.println("Loan Period: "
                + t.getLoanPeriodDays());

        System.out.println(t.renew());
        System.out.println(t.reserve());

        System.out.println();

        Magazine m = new Magazine("Tech Monthly");

        System.out.println(reserveIfSupported(m));

        System.out.println();

        DigitalPass d = new DigitalPass("E-Journal Access");

        System.out.println(reserveIfSupported(d));

        System.out.println();

        LibraryItem ref = t; // Upcasting

        System.out.println(reserveIfSupported(ref));

        System.out.println();

        LibraryItem[] items = {
            t,
            m
        };

        processCheckouts(items);
    }
}
