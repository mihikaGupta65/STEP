
class EventTicket {
    private static int ticketCounter = 1000;

    private final String ticketId;
    private double basePrice;
    private String paymentMode;

    public EventTicket(double basePrice) {
        ticketCounter++;
        ticketId = "TCK-" + ticketCounter;
        this.basePrice = basePrice;
        paymentMode = "Not specified";
    }

    public void pay(double amount) {
        if (amount > 0) {
            basePrice -= amount;

            if (basePrice < 0) {
                basePrice = 0;
            }
        }
    }

    public void pay(double amount, String mode) {
        paymentMode = mode;
        System.out.println("Paying via " + mode);
        pay(amount);
    }

    public double getBalanceDue() {
        return basePrice;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public static int getTicketsIssued() {
        return ticketCounter - 1000;
    }

    public static boolean isValidPromoCode(String code) {
        if (code == null || code.length() != 5) {
            return false;
        }

        if (code.charAt(0) != 'F') {
            return false;
        }

        if (!Character.isDigit(code.charAt(1))
                || !Character.isDigit(code.charAt(2))
                || !Character.isDigit(code.charAt(3))) {
            return false;
        }

        if (!Character.isUpperCase(code.charAt(4))) {
            return false;
        }

        return true;
    }

    public static String processNightlySettlement(EventTicket[] tickets) {
        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;

        for (EventTicket ticket : tickets) {
            if (ticket == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (ticket instanceof GroupTicket) {
                group++;
            } else {
                individual++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + group + " group | "
                + individual + " individual";
    }
}

class GroupTicket extends EventTicket {
    private int groupSize;

    public GroupTicket(double basePrice, int groupSize) {
        super(basePrice);

        if (groupSize <= 0) {
            throw new IllegalArgumentException("Group size must be positive");
        }

        this.groupSize = groupSize;
    }

    public int getGroupSize() {
        return groupSize;
    }
}

public class Pb5 {
    public static void main(String[] args) {
        EventTicket t1 = new EventTicket(500);

        System.out.println("Ticket ID: " + t1.getTicketId());
        System.out.println("Tickets Issued: "
                + EventTicket.getTicketsIssued());

        System.out.println();

        System.out.println(EventTicket.isValidPromoCode("F123A"));
        System.out.println(EventTicket.isValidPromoCode("F12A"));
        System.out.println(EventTicket.isValidPromoCode("X123A"));

        System.out.println();

        t1.pay(200);
        t1.pay(200, "UPI");

        System.out.println("Balance Due: " + t1.getBalanceDue());

        System.out.println();

        EventTicket[] tickets = {
            new GroupTicket(2000, 5),
            null,
            new EventTicket(500)
        };

        System.out.println(
                EventTicket.processNightlySettlement(tickets));
    }
}
