
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

    public String getBibNumber() {
        return bibNumber;
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

    public void announce() {
        System.out.println("Race Entry | Bib: " + bibNumber
                + " | Balance: " + getBalanceDue());
    }

    public static String classifyGeneration(RaceEntry entry) {
        if (entry instanceof EliteRunnerEntry) {
            return "Multilevel descendant (3 generations deep)";
        } else if (entry instanceof RunnerEntry) {
            return "Single inheritance descendant";
        } else if (entry instanceof RelayTeamEntry) {
            return "Hierarchical sibling (independent branch)";
        } else {
            return "Base race entry";
        }
    }

    public static double getTotalBalanceDue(RaceEntry[] entries) {
        double total = 0;

        for (RaceEntry entry : entries) {
            total += entry.getBalanceDue();
        }

        return total;
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

    @Override
    public void announce() {
        System.out.println("Runner Entry | Bib: " + getBibNumber()
                + " | Category: " + category
                + " | Balance: " + getBalanceDue());
    }
}

class EliteRunnerEntry extends RunnerEntry {
    private double sponsorBonus;

    public EliteRunnerEntry(String bibNumber, double entryFee,
                            String category, double sponsorBonus) {
        super(bibNumber, entryFee, category);
        this.sponsorBonus = sponsorBonus;
    }

    @Override
    public void announce() {
        System.out.println("Elite Runner | Bib: " + getBibNumber()
                + " | Category: " + getCategory()
                + " | Sponsor Bonus: " + sponsorBonus
                + " | Balance: " + getBalanceDue());
    }
}

class RelayTeamEntry extends RaceEntry {
    private int teamSize;

    public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
        super(bibNumber, entryFee);
        this.teamSize = teamSize;
    }

    @Override
    public void announce() {
        System.out.println("Relay Team | Bib: " + getBibNumber()
                + " | Team Size: " + teamSize
                + " | Balance: " + getBalanceDue());
    }
}

public class Pb2 {
    public static void main(String[] args) {
        RunnerEntry runner = new RunnerEntry(
                "BIB2001", 80, "Open 10K");

        EliteRunnerEntry elite = new EliteRunnerEntry(
                "BIB3001", 150, "Elite Full Marathon", 500);

        RelayTeamEntry relay = new RelayTeamEntry(
                "BIB4001", 300, 4);

        runner.announce();
        elite.announce();
        relay.announce();

        System.out.println();

        System.out.println(RaceEntry.classifyGeneration(elite));
        System.out.println(RaceEntry.classifyGeneration(relay));

        System.out.println();

        RaceEntry[] entries = {runner, elite, relay};

        System.out.println("Total Balance Due: "
                + RaceEntry.getTotalBalanceDue(entries));
    }
}
