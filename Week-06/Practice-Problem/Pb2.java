
class EventTicket {
    private String attendeeId;
    private double basePrice;

    public EventTicket(String attendeeId, double basePrice) {
        if (attendeeId == null || attendeeId.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid attendee ID");
        }

        if (basePrice <= 0) {
            throw new IllegalArgumentException("Invalid base price");
        }

        this.attendeeId = attendeeId;
        this.basePrice = basePrice;
    }

    public String getAttendeeId() {
        return attendeeId;
    }

    public double getBalanceDue() {
        return basePrice;
    }

    public void pay(double amount) {
        if (amount > 0) {
            basePrice -= amount;

            if (basePrice < 0) {
                basePrice = 0;
            }
        }
    }

    public void printTicket() {
        System.out.println("Standard Event Ticket | Balance Due: "
                + getBalanceDue());
    }

    public static String classifyGeneration(EventTicket ticket) {
        if (ticket instanceof PremiumWorkshopTicket) {
            return "Multilevel descendant (3 generations deep)";
        } else if (ticket instanceof WorkshopTicket) {
            return "Single inheritance descendant";
        } else if (ticket instanceof HackathonTicket) {
            return "Hierarchical sibling (independent branch)";
        } else {
            return "Base event ticket";
        }
    }

    public static double getTotalBalanceDue(EventTicket[] tickets) {
        double total = 0;

        for (EventTicket ticket : tickets) {
            total += ticket.getBalanceDue();
        }

        return total;
    }
}

class WorkshopTicket extends EventTicket {
    private String track;

    public WorkshopTicket(String attendeeId, double basePrice, String track) {
        super(attendeeId, basePrice);
        this.track = track;
    }

    public String getTrack() {
        return track;
    }

    @Override
    public void printTicket() {
        System.out.println("Workshop Ticket | Track: " + track
                + " | Balance Due: " + getBalanceDue());
    }
}

class PremiumWorkshopTicket extends WorkshopTicket {
    private double kitFee;

    public PremiumWorkshopTicket(String attendeeId, double basePrice,
                                 String track, double kitFee) {
        super(attendeeId, basePrice, track);
        this.kitFee = kitFee;
    }

    @Override
    public void printTicket() {
        System.out.println("Premium Workshop Ticket | Track: "
                + getTrack() + " | Kit Fee: " + kitFee
                + " | Balance Due: " + getBalanceDue());
    }
}

class HackathonTicket extends EventTicket {
    private String teamName;

    public HackathonTicket(String attendeeId, double basePrice,
                           String teamName) {
        super(attendeeId, basePrice);
        this.teamName = teamName;
    }

    @Override
    public void printTicket() {
        System.out.println("Hackathon Ticket | Team: " + teamName
                + " | Balance Due: " + getBalanceDue());
    }
}

public class Pb2 {
    public static void main(String[] args) {
        EventTicket standard = new EventTicket("STU1", 500);

        WorkshopTicket workshop = new WorkshopTicket(
                "STU2", 1200, "AI/ML");

        PremiumWorkshopTicket premium = new PremiumWorkshopTicket(
                "STU3", 2000, "Cloud Native", 300);

        HackathonTicket hackathon = new HackathonTicket(
                "STU4", 800, "Byte Force");

        standard.printTicket();
        workshop.printTicket();
        premium.printTicket();
        hackathon.printTicket();

        System.out.println();

        System.out.println(
                EventTicket.classifyGeneration(premium));

        System.out.println(
                EventTicket.classifyGeneration(hackathon));

        System.out.println();

        EventTicket[] tickets = {
            standard, workshop, premium, hackathon
        };

        System.out.println("Total Balance Due: "
                + EventTicket.getTotalBalanceDue(tickets));
    }
}
