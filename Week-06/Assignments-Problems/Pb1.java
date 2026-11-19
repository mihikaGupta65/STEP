
class RaceEntry {
    private String bibNumber;
    private double entryFee;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid bib number");
        }

        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
    }

    public void pay(double amount) {
        if (amount > 0) {
            entryFee -= amount;
            if (entryFee < 0) {
                entryFee = 0;
            }
        }
    }

    public double getBalanceDue() {
        return entryFee;
    }

    public static String registerBatch(String[] bibNumbers, double entryFee) {
        int registered = 0;
        int rejected = 0;

        for (String bib : bibNumbers) {
            try {
                new RaceEntry(bib, entryFee);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Registered: " + registered + " | Rejected: " + rejected;
    }
}

class RunnerEntry extends RaceEntry {
    private String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}

public class Pb1 {
    public static void main(String[] args) {
        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");

        r.pay(30);
        System.out.println("Balance Due: " + r.getBalanceDue());

        String[] bibNumbers = {"BIB1", "B1", "BIB2"};

        System.out.println(RaceEntry.registerBatch(bibNumbers, 80));
    }
}
