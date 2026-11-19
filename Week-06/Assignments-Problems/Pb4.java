
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
        System.out.print("Race Entry | Bib: " + bibNumber
                + " | Balance: " + getBalanceDue());
    }

    public static String announceAll(RaceEntry[] entries) {
        StringBuilder report = new StringBuilder();

        for (RaceEntry entry : entries) {
            StringBuilder announcement = new StringBuilder();

            if (entry instanceof RunnerEntry) {
                RunnerEntry runner = (RunnerEntry) entry;
                announcement.append("Runner Entry | Bib: ")
                        .append(runner.getBibNumber())
                        .append(" | Category: ")
                        .append(runner.getCategory())
                        .append(" | Balance: ")
                        .append(runner.getBalanceDue());
            } else if (entry instanceof RelayTeamEntry) {
                RelayTeamEntry relay = (RelayTeamEntry) entry;
                announcement.append("Relay Team | Bib: ")
                        .append(relay.getBibNumber())
                        .append(" | Team Size: ")
                        .append(relay.getTeamSize())
                        .append(" | Balance: ")
                        .append(relay.getBalanceDue());
            } else {
                announcement.append("Race Entry | Bib: ")
                        .append(entry.getBibNumber())
                        .append(" | Balance: ")
                        .append(entry.getBalanceDue());
            }

            report.append(announcement).append(" | ");

            if (entry instanceof RelayTeamEntry) {
                RelayTeamEntry relay = (RelayTeamEntry) entry;
                report.append("[Team size via downcast: ")
                        .append(relay.getTeamSize())
                        .append("] | ");
            }
        }

        return report.toString();
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
        System.out.print("Runner Entry | Bib: " + getBibNumber()
                + " | Category: " + category
                + " | Balance: " + getBalanceDue());
    }
}

class RelayTeamEntry extends RaceEntry {
    private int teamSize;

    public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
        super(bibNumber, entryFee);
        this.teamSize = teamSize;
    }

    public int getTeamSize() {
        return teamSize;
    }

    @Override
    public void announce() {
        System.out.print("Relay Team | Bib: " + getBibNumber()
                + " | Team Size: " + teamSize
                + " | Balance: " + getBalanceDue());
    }
}

public class Pb4 {
    public static void main(String[] args) {
        RunnerEntry runner = new RunnerEntry(
                "BIB2001", 90, "Open 10K");

        RelayTeamEntry relay = new RelayTeamEntry(
                "BIB4001", 300, 4);

        RaceEntry[] fleet = {runner, relay};

        System.out.println(RaceEntry.announceAll(fleet));

        RaceEntry plain = new RaceEntry("BIB5001", 50);

        try {
            RelayTeamEntry bad = (RelayTeamEntry) plain;
        } catch (ClassCastException e) {
            System.out.println("ClassCastException at runtime");
        }
    }
}
