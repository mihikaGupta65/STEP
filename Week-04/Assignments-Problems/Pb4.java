
final class SurgeFeeCalculator {

    private final double minimumSurgePercent;

    public SurgeFeeCalculator(double minimumSurgePercent) {

        if (minimumSurgePercent < 0) {
            throw new IllegalArgumentException("Surge percentage cannot be negative");
        }

        this.minimumSurgePercent = minimumSurgePercent;
    }

    public final double calculateSurgeFee(double orderValue, int delayMinutes) {

        if (orderValue < 0 || delayMinutes < 0) {
            throw new IllegalArgumentException("Order value and delay cannot be negative");
        }

        if (delayMinutes == 0) {
            return 0.0;
        }

        int firstTier = Math.min(delayMinutes, 5);
        int secondTier = Math.min(Math.max(delayMinutes - 5, 0), 10);
        int thirdTier = Math.max(delayMinutes - 15, 0);

        double surgeFee = 0.0;

        surgeFee += firstTier * orderValue * 0.005;
        surgeFee += secondTier * orderValue * 0.01;
        surgeFee += thirdTier * orderValue * 0.02;

        double minimumSurge = orderValue * minimumSurgePercent / 100.0;

        return Math.max(surgeFee, minimumSurge);
    }
}

public class Pb4 {

    public static void main(String[] args) {

        SurgeFeeCalculator calculator = new SurgeFeeCalculator(1.0);

        System.out.println("Surge fee for 0 minutes: Rs " +
                calculator.calculateSurgeFee(500, 0));

        System.out.println("Surge fee for 1 minute: Rs " +
                calculator.calculateSurgeFee(500, 1));

        System.out.println("Surge fee for 16 minutes: Rs " +
                calculator.calculateSurgeFee(500, 16));
    }
}
