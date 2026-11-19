
class DeliveryAccount {

    private String studentId;
    private double orderValue;

    private static int totalAccounts;

    static {
        totalAccounts = 0;
        System.out.println("Multi-Kitchen Reconciliation System Initialized");
    }

    public DeliveryAccount(String studentId, double orderValue) {

        if (studentId == null || studentId.trim().isEmpty() || orderValue < 0) {
            throw new IllegalArgumentException("Invalid account details");
        }

        this.studentId = studentId;
        this.orderValue = orderValue;
        totalAccounts++;
    }

    public DeliveryAccount(String studentId) {
        this(studentId, 0.0);
    }

    public final double calculateSurgeFee(int delayMinutes) {

        if (delayMinutes < 0) {
            throw new IllegalArgumentException("Delay cannot be negative");
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

        double minimumSurge = orderValue * 0.01;

        return Math.max(surgeFee, minimumSurge);
    }

    public void processAccount(DeliveryAccount account, double amount, int delayMinutes) {

        if (account == null) {
            return;
        }

        if (amount < 0 || delayMinutes < 0) {
            throw new IllegalArgumentException("Invalid amount or delay");
        }

        double fee = account.calculateSurgeFee(delayMinutes);

        System.out.println("Student ID: " + account.studentId);
        System.out.println("Surge Fee: Rs " + fee);
    }

    public String getStudentId() {
        return studentId;
    }
}

class Premium extends DeliveryAccount {

    public Premium(String studentId, double orderValue) {
        super(studentId, orderValue);
    }

    public void settle(double amount, double fee) {
        System.out.println("Premium settlement: Rs " + (amount + fee));
    }
}

public class Pb5 {

    static void processBatch(DeliveryAccount[] accounts,
                             double[] amounts,
                             int[] delayMinutesArray) {

        if (accounts == null || amounts == null || delayMinutesArray == null) {
            System.out.println("Invalid batch: arrays cannot be null");
            return;
        }

        if (accounts.length != amounts.length ||
            accounts.length != delayMinutesArray.length) {

            System.out.println("Invalid batch: array lengths do not match");
            return;
        }

        int processed = 0;
        int nullSkipped = 0;
        int premiumCount = 0;
        int regularCount = 0;

        double grandTotal = 0.0;

        for (int i = 0; i < accounts.length; i++) {

            DeliveryAccount account = accounts[i];

            if (account == null) {
                nullSkipped++;
                continue;
            }

            try {

                if (amounts[i] < 0) {
                    throw new IllegalArgumentException("Amount cannot be negative");
                }

                double fee = account.calculateSurgeFee(delayMinutesArray[i]);

                if (account instanceof Premium) {
                    Premium premium = (Premium) account;
                    premium.settle(amounts[i], fee);
                    premiumCount++;
                } else {
                    System.out.println("Regular settlement: Rs " +
                            (amounts[i] + fee));
                    regularCount++;
                }

                grandTotal += fee;
                processed++;

            } catch (IllegalArgumentException e) {

                System.out.println("Invalid account at index " + i +
                        ": " + e.getMessage());
            }
        }

        System.out.println("\nNightly Reconciliation Summary");
        System.out.println(processed + " processed");
        System.out.println(nullSkipped + " null skipped");
        System.out.println(premiumCount + " premium");
        System.out.println(regularCount + " regular");
        System.out.println("Grand total surge fees = Rs " + grandTotal);
    }

    public static void main(String[] args) {

        DeliveryAccount[] accounts = {
            new Premium("STU001", 500),
            null,
            new DeliveryAccount("STU002", 300)
        };

        double[] amounts = {500, 400, 300};
        int[] delayMinutesArray = {10, 5, 0};

        processBatch(accounts, amounts, delayMinutesArray);
    }
}
