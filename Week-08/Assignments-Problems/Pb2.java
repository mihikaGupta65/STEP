
import java.util.ArrayList;
import java.util.List;

interface ShippingType {
    double calculateCharge(double weight);
}

class StandardShipping implements ShippingType {
    @Override
    public double calculateCharge(double weight) {
        return 40 + (10 * weight);
    }
}

class ExpressShipping implements ShippingType {
    @Override
    public double calculateCharge(double weight) {
        return 80 + (15 * weight);
    }
}

class FragileShipping implements ShippingType {
    private ShippingType standard = new StandardShipping();

    @Override
    public double calculateCharge(double weight) {
        return standard.calculateCharge(weight) + 50;
    }
}

interface NotificationChannel {
    void sendNotification(String parcelId, ParcelStatus status);
}

class SmsChannel implements NotificationChannel {
    @Override
    public void sendNotification(String parcelId, ParcelStatus status) {
        System.out.println("[SMS] " + parcelId + " is now " + status + ".");
    }
}

class EmailChannel implements NotificationChannel {
    @Override
    public void sendNotification(String parcelId, ParcelStatus status) {
        System.out.println("[Email] " + parcelId + " is now " + status + ".");
    }
}

enum ParcelStatus {
    BOOKED,
    PICKED_UP,
    IN_TRANSIT,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED
}

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Parcel {
    private String parcelId;
    private Customer customer;
    private double weight;
    private ShippingType shippingType;
    private ParcelStatus status;
    private List<NotificationChannel> channels;

    public Parcel(String parcelId, Customer customer,
                  double weight, ShippingType shippingType) {
        this.parcelId = parcelId;
        this.customer = customer;
        this.weight = weight;
        this.shippingType = shippingType;
        this.status = ParcelStatus.BOOKED;
        channels = new ArrayList<>();
    }

    public String getParcelId() {
        return parcelId;
    }

    public ParcelStatus getStatus() {
        return status;
    }

    public double calculateCharge() {
        return shippingType.calculateCharge(weight);
    }

    public void subscribe(NotificationChannel channel) {
        if (channel != null) {
            channels.add(channel);
        }
    }

    private void notifyChannels() {
        for (NotificationChannel channel : channels) {
            channel.sendNotification(parcelId, status);
        }
    }

    public void updateStatus(ParcelStatus newStatus) {
        if (status == ParcelStatus.CANCELLED
                || status == ParcelStatus.DELIVERED) {
            System.out.println("Invalid transition: " + status
                    + " → " + newStatus + " is not allowed.");
            return;
        }

        if (newStatus.ordinal() != status.ordinal() + 1) {
            System.out.println("Invalid transition: " + status
                    + " → " + newStatus + " is not allowed.");
            return;
        }

        status = newStatus;
        notifyChannels();
    }

    public void cancel() {
        if (status != ParcelStatus.BOOKED) {
            System.out.println("Cancellation failed: " + parcelId
                    + " can be cancelled only while BOOKED.");
            return;
        }

        status = ParcelStatus.CANCELLED;
        notifyChannels();
        System.out.println("Parcel " + parcelId + " cancelled successfully.");
    }
}

class ParcelService {
    private List<Parcel> parcels;

    public ParcelService() {
        parcels = new ArrayList<>();
    }

    public Parcel bookParcel(String parcelId, Customer customer,
                             double weight, ShippingType shippingType) {
        if (weight <= 0 || shippingType == null) {
            System.out.println("Invalid parcel details.");
            return null;
        }

        for (Parcel parcel : parcels) {
            if (parcel.getParcelId().equals(parcelId)) {
                System.out.println("Parcel ID already exists.");
                return null;
            }
        }

        Parcel parcel = new Parcel(
                parcelId, customer, weight, shippingType);

        parcels.add(parcel);

        System.out.println("Parcel " + parcelId + " booked ("
                + shippingType.getClass().getSimpleName().replace("Shipping", "")
                + ", " + weight + " kg).");

        System.out.printf("Charge: ₹%.2f%n", parcel.calculateCharge());

        return parcel;
    }
}

public class Pb2 {
    public static void main(String[] args) {

        ParcelService service = new ParcelService();

        Customer customer = new Customer("Rahul");

        Parcel parcel = service.bookParcel(
                "P101", customer, 2, new ExpressShipping());

        if (parcel == null) {
            return;
        }

        parcel.subscribe(new SmsChannel());
        parcel.subscribe(new EmailChannel());

        parcel.updateStatus(ParcelStatus.BOOKED);

        parcel.updateStatus(ParcelStatus.PICKED_UP);

        parcel.cancel();

        parcel.updateStatus(ParcelStatus.IN_TRANSIT);

        parcel.updateStatus(ParcelStatus.DELIVERED);
    }
}
