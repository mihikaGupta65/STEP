
import java.util.Arrays;

class EventTicket {
    private String attendeeId;
    private double basePrice;
    private double[] lateFeeHistory;
    private int penaltyCount;

    public EventTicket(String attendeeId, double basePrice) {
        if (attendeeId == null || attendeeId.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid attendee ID");
        }

        if (basePrice <= 0) {
            throw new IllegalArgumentException("Invalid base price");
        }

        this.attendeeId = attendeeId;
        this.basePrice = basePrice;
        lateFeeHistory = new double[10];
        penaltyCount = 0;
    }

    public void pay(double amount) {
        if (amount > 0) {
            basePrice -= amount;

            if (basePrice < 0) {
                basePrice = 0;
            }
        }
    }

    protected void applyLateFee(double amount) {
        basePrice += amount;

        if (penaltyCount < 10) {
            lateFeeHistory[penaltyCount] = amount;
            penaltyCount++;
        }
    }

    public double getBalanceDue() {
        return basePrice;
    }

    public double[] getLateFeeHistory() {
        return Arrays.copyOf(lateFeeHistory, penaltyCount);
    }
}

class WorkshopTicket extends EventTicket {
    private String track;

    public WorkshopTicket(String attendeeId, double basePrice, String track) {
        super(attendeeId, basePrice);
        this.track = track;
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}

public class Pb3 {
    public static void main(String[] args) {
        WorkshopTicket w = new WorkshopTicket("STU2", 1200, "AI/ML");

        w.pay(1200);
        w.applyLateFee(100);

        System.out.println("Balance Due: " + w.getBalanceDue());

        double[] history = w.getLateFeeHistory();
        System.out.println("Penalty History: " + Arrays.toString(history));

        history[0] = 999;

        System.out.println("After Tampering: "
                + Arrays.toString(w.getLateFeeHistory()));
    }
}
