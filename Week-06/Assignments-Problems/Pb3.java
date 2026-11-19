
import java.util.Arrays;

class RaceEntry {
    private String bibNumber;
    private double entryFee;
    private double[] lateFeeHistory;
    private int penaltyCount;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid bib number");
        }

        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
        this.lateFeeHistory = new double[10];
        this.penaltyCount = 0;
    }

    public void pay(double amount) {
        if (amount > 0) {
            entryFee -= amount;

            if (entryFee < 0) {
                entryFee = 0;
            }
        }
    }

    protected void applyLateFee(double amount) {
        entryFee += amount;

        if (penaltyCount < 10) {
            lateFeeHistory[penaltyCount] = amount;
            penaltyCount++;
        }
    }

    public double getBalanceDue() {
        return entryFee;
    }

    public double[] getLateFeeHistory() {
        return Arrays.copyOf(lateFeeHistory, penaltyCount);
    }
}

class RunnerEntry extends RaceEntry {
    private String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}

public class Pb3 {
    public static void main(String[] args) {
        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");

        r.pay(30);
        r.applyLateFee(20);

        System.out.println("Balance Due: " + r.getBalanceDue());

        double[] history = r.getLateFeeHistory();
        System.out.println("Penalty History: " + Arrays.toString(history));

        history[0] = 999;

        System.out.println("After Tampering: "
                + Arrays.toString(r.getLateFeeHistory()));
    }
}
