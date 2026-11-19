
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

    public void pay(double amount) {
        if (amount > 0) {
            basePrice -= amount;

            if (basePrice < 0) {
                basePrice = 0;
            }
        }
    }

    public double getBalanceDue() {
        return basePrice;
    }

    public static String registerBatch(String[] attendeeIds, double basePrice) {
        int registered = 0;
        int rejected = 0;

        for (String id : attendeeIds) {
            try {
                new EventTicket(id, basePrice);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Registered: " + registered + " | Rejected: " + rejected;
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
}

public class Pb1 {
    public static void main(String[] args) {
        WorkshopTicket w = new WorkshopTicket("STU2", 1200, "AI/ML");

        w.pay(500);
        System.out.println("Balance Due: " + w.getBalanceDue());

        String[] attendeeIds = {"STU1", "ST1", "STU2", " ", "STU3"};

        System.out.println(EventTicket.registerBatch(attendeeIds, 500));
    }
}
