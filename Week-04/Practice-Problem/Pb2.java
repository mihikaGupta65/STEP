
import java.util.Arrays;

class FareSplitter {

    private String tripId;
    private double totalFare;
    private int passengerCount;

    FareSplitter(String tripId, double totalFare, int passengerCount) {

        if (totalFare < 0 || !Double.isFinite(totalFare)) {
            throw new IllegalArgumentException("Invalid fare");
        }

        if (passengerCount <= 0) {
            throw new IllegalArgumentException("Passenger count must be positive");
        }

        this.tripId = tripId;
        this.totalFare = totalFare;
        this.passengerCount = passengerCount;
    }

    FareSplitter(String tripId, double totalFare) {
        this(tripId, totalFare, 2);
    }

    FareSplitter(String tripId) {
        this(tripId, 0.0, 2);
    }

    double[] fareBreakdown() {

        double[] shares = new double[passengerCount];

        long totalPaise = Math.round(totalFare * 100);

        long basicShare = totalPaise / passengerCount;
        long remainder = totalPaise % passengerCount;

        for (int i = 0; i < passengerCount; i++) {
            long share = basicShare;

            if (i == passengerCount - 1) {
                share += remainder;
            }

            shares[i] = share / 100.0;
        }

        return shares;
    }

    boolean isConfirmationOverdue(int confirmed, int expected) {
        return confirmed < expected;
    }
}

public class Pb2 {

    public static void main(String[] args) {

        FareSplitter trip1 = new FareSplitter("TRIP001", 100000, 3);

        System.out.println("Trip 1 Fare Breakdown:");
        System.out.println(Arrays.toString(trip1.fareBreakdown()));

        FareSplitter trip2 = new FareSplitter("TRIP002", 6000);

        System.out.println("\nTrip 2 Fare Breakdown:");
        System.out.println(Arrays.toString(trip2.fareBreakdown()));

        FareSplitter trip3 = new FareSplitter("TRIP003");

        System.out.println("\nTrip 3 Fare Breakdown:");
        System.out.println(Arrays.toString(trip3.fareBreakdown()));

        System.out.println("\nConfirmation overdue: "
                + trip1.isConfirmationOverdue(2, 3));

        System.out.println("Confirmation overdue: "
                + trip2.isConfirmationOverdue(2, 2));
    }
}
