
final class BoardingPenaltyCalculator {

    private final double minimumPenaltyPercent;

    public BoardingPenaltyCalculator(double minimumPenaltyPercent) {
        if (minimumPenaltyPercent < 0) {
            throw new IllegalArgumentException("Minimum penalty cannot be negative");
        }

        this.minimumPenaltyPercent = minimumPenaltyPercent;
    }

    public final double calculatePenalty(double ticketFare, int minutesLate) {

        if (ticketFare < 0 || minutesLate < 0) {
            throw new IllegalArgumentException("Fare and minutes late cannot be negative");
        }

        if (minutesLate == 0) {
            return 0.0;
        }

        double penalty = 0.0;

        int firstTier = Math.min(minutesLate, 5);
        int secondTier = Math.min(Math.max(minutesLate - 5, 0), 10);
        int thirdTier = Math.max(minutesLate - 15, 0);

        penalty += firstTier * ticketFare * 0.005;
        penalty += secondTier * ticketFare * 0.01;
        penalty += thirdTier * ticketFare * 0.02;

        double minimumPenalty = ticketFare * minimumPenaltyPercent / 100.0;

        return Math.max(penalty, minimumPenalty);
    }
}

public class Pb4 {

    public static void main(String[] args) {

        BoardingPenaltyCalculator calculator =
                new BoardingPenaltyCalculator(1.0);

        System.out.println("Penalty for 0 minutes: Rs " +
                calculator.calculatePenalty(1000, 0));

        System.out.println("Penalty for 1 minute: Rs " +
                calculator.calculatePenalty(1000, 1));

        System.out.println("Penalty for 16 minutes: Rs " +
                calculator.calculatePenalty(1000, 16));
    }
}
