
class RaceEntry {
    private static int bibCounter = 0;

    private final String entryCode;
    private String bibNumber;
    private double entryFee;
    private String paymentMode;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid bib number");
        }

        if (entryFee <= 0) {
            throw new IllegalArgumentException("Invalid entry fee");
        }

        bibCounter++;
        this.entryCode = "ENTRY" + bibCounter;
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
        this.paymentMode = "Not specified";
    }

    public void pay(double amount) {
        if (amount > 0) {
            entryFee -= amount;

            if (entryFee < 0) {
                entryFee = 0;
            }
        }
    }

    public void pay(double amount, String mode) {
        pay(amount);
        this.paymentMode = mode;
        System.out.println("Paying via " + mode);
    }

    public double getBalanceDue() {
        return entryFee;
    }

    public String getBibNumber() {
        return bibNumber;
    }

    public String getEntryCode() {
        return entryCode;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public static boolean isValidDiscountCode(String code) {
        if (code == null || code.length() != 5) {
            return false;
        }

        if (code.charAt(0) != 'M') {
            return false;
        }

        if (!Character.isDigit(code.charAt(1))) {
            return false;
        }

        if (!Character.isDigit(code.charAt(2))) {
            return false;
        }

        if (!Character.isDigit(code.charAt(3))) {
            return false;
        }

        if (!Character.isUpperCase(code.charAt(4))) {
            return false;
        }

        return true;
    }

    public static int getBibCounter() {
        return bibCounter;
    }

    public static String settleNight(RaceEntry[] entries) {
        int processed = 0;
        int nullSkipped = 0;
        int relayCount = 0;
        int individualCount = 0;

        for (RaceEntry entry : entries) {
            if (entry == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (entry instanceof RelayTeamEntry) {
                relayCount++;
            } else {
                individualCount++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + relayCount + " relay | "
                + individualCount + " individual";
    }
}

class RunnerEntry extends RaceEntry {
    private String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }
}

class EliteRunnerEntry extends RunnerEntry {
    private double sponsorBonus;

    public EliteRunnerEntry(String bibNumber, double entryFee,
                            String category, double sponsorBonus) {
        super(bibNumber, entryFee, category);
        this.sponsorBonus = sponsorBonus;
    }
}

class RelayTeamEntry extends RaceEntry {
    private int teamSize;

    public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
        super(bibNumber, entryFee);

        if (teamSize <= 0) {
            throw new IllegalArgumentException("Invalid team size");
        }

        this.teamSize = teamSize;
    }
}

public class Pb5 {
    public static void main(String[] args) {
        System.out.println(RaceEntry.isValidDiscountCode("M123A"));
        System.out.println(RaceEntry.isValidDiscountCode("M12A"));
        System.out.println(RaceEntry.isValidDiscountCode("X123A"));

        System.out.println();

        RunnerEntry runner = new RunnerEntry(
                "BIB2001", 80, "Open 10K");

        EliteRunnerEntry elite = new EliteRunnerEntry(
                "BIB3001", 150, "Elite Full Marathon", 500);

        RelayTeamEntry relay = new RelayTeamEntry(
                "BIB4001", 300, 4);

        runner.pay(10, "UPI");

        System.out.println(RaceEntry.settleNight(
                new RaceEntry[]{elite, null, relay}));

        System.out.println();

        System.out.println("Entry Code: " + runner.getEntryCode());
        System.out.println("Entry Code: " + elite.getEntryCode());
        System.out.println("Entry Code: " + relay.getEntryCode());

        System.out.println("Bib Counter: " + RaceEntry.getBibCounter());
    }
}
