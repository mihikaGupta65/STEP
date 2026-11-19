
class BusTicketAccount {

    private String bookingId;
    private double ticketFare;

    private static int totalAccountsCreated;

    static {
        totalAccountsCreated = 0;
        System.out.println("Fleet Reconciliation System Initialized");
    }

    public BusTicketAccount(String bookingId, double ticketFare) {

        if (bookingId == null || bookingId.trim().isEmpty() || ticketFare < 0) {
            throw new IllegalArgumentException("Invalid booking details");
        }

        this.bookingId = bookingId;
        this.ticketFare = ticketFare;
        totalAccountsCreated++;
    }

    public BusTicketAccount(String bookingId) {
        this(bookingId, 0.0);
    }

    public final double calculatePenalty(int minutesLate) {

        if (minutesLate < 0) {
            throw new IllegalArgumentException("Minutes late cannot be negative");
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

        double minimumPenalty = ticketFare * 0.01;

        return Math.max(penalty, minimumPenalty);
    }

    public void processAccount(BusTicketAccount account, double amount, int minutesLate) {

        if (account == null) {
            return;
        }

        if (amount < 0 || minutesLate < 0) {
            throw new IllegalArgumentException("Invalid amount or delay");
        }

        double penalty = account.calculatePenalty(minutesLate);

        System.out.println("Booking ID: " + account.bookingId);
        System.out.println("Penalty: Rs " + penalty);
    }

    public String getBookingId() {
        return bookingId;
    }
}

class Sleeper extends BusTicketAccount {

    public Sleeper(String bookingId, double ticketFare) {
        super(bookingId, ticketFare);
    }

    public void settle(double amount, double penalty) {
        System.out.println("Sleeper settlement: Rs " + (amount + penalty));
    }
}

public class Pb5 {

    static void processBatch(BusTicketAccount[] accounts,
                             double[] amounts,
                             int[] minutesLateArray) {

        if (accounts == null || amounts == null || minutesLateArray == null) {
            System.out.println("Invalid batch: arrays cannot be null");
            return;
        }

        if (accounts.length != amounts.length ||
            accounts.length != minutesLateArray.length) {

            System.out.println("Invalid batch: array lengths do not match");
            return;
        }

        int processed = 0;
        int nullSkipped = 0;
        int sleeperCount = 0;
        int regularCount = 0;

        double grandTotal = 0.0;

        for (int i = 0; i < accounts.length; i++) {

            BusTicketAccount account = accounts[i];

            if (account == null) {
                nullSkipped++;
                continue;
            }

            try {
                double penalty = account.calculatePenalty(minutesLateArray[i]);

                if (amounts[i] < 0) {
                    throw new IllegalArgumentException("Invalid amount");
                }

                if (account instanceof Sleeper) {
                    Sleeper sleeper = (Sleeper) account;
                    sleeper.settle(amounts[i], penalty);
                    sleeperCount++;
                } else {
                    System.out.println("Regular settlement: Rs " +
                            (amounts[i] + penalty));
                    regularCount++;
                }

                grandTotal += penalty;
                processed++;

            } catch (IllegalArgumentException e) {
                System.out.println("Invalid account at index " + i +
                        ": " + e.getMessage());
            }
        }

        System.out.println("\nFleet Reconciliation Summary");
        System.out.println(processed + " processed");
        System.out.println(nullSkipped + " null skipped");
        System.out.println(sleeperCount + " sleeper");
        System.out.println(regularCount + " regular");
        System.out.println("Grand total penalties = Rs " + grandTotal);
    }

    public static void main(String[] args) {

        BusTicketAccount[] accounts = {
            new Sleeper("BK001", 2000),
            null,
            new BusTicketAccount("BK002", 1200)
        };

        double[] amounts = {1200, 900, 700};
        int[] minutesLateArray = {10, 5, 0};

        processBatch(accounts, amounts, minutesLateArray);
    }
}
