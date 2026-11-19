
import java.util.HashSet;
import java.util.Set;

class BusTicket {

    private String passengerName;
    private String destination;
    private boolean checkedIn = false;

    BusTicket(String passengerName, String destination) {

        if (passengerName == null || destination == null) {
            throw new IllegalArgumentException("Name or destination cannot be null");
        }

        passengerName = passengerName.trim();
        destination = destination.trim();

        if (passengerName.isEmpty() || destination.isEmpty()) {
            throw new IllegalArgumentException("Name or destination cannot be blank");
        }

        if (!passengerName.matches("[A-Za-z ]+")) {
            throw new IllegalArgumentException("Invalid passenger name");
        }

        if (!destination.matches("[A-Za-z ]+")) {
            throw new IllegalArgumentException("Invalid destination");
        }

        this.passengerName = passengerName;
        this.destination = destination;
    }

    void markCheckedIn() {
        if (!checkedIn) {
            checkedIn = true;
            System.out.println(passengerName + " checked in successfully");
        } else {
            System.out.println(passengerName + " is already checked in");
        }
    }

    static void processBatch(String[][] rawBookings) {

        Set<String> acceptedBookings = new HashSet<>();

        int valid = 0;
        int rejected = 0;
        int duplicates = 0;

        if (rawBookings == null) {
            System.out.println("Valid: 0 | Rejected: 0 | Duplicates skipped: 0");
            return;
        }

        for (String[] booking : rawBookings) {

            try {
                if (booking == null || booking.length < 2) {
                    throw new IllegalArgumentException();
                }

                BusTicket ticket = new BusTicket(booking[0], booking[1]);

                String key = ticket.passengerName.toLowerCase()
                        + "|" + ticket.destination.toLowerCase();

                if (acceptedBookings.contains(key)) {
                    duplicates++;
                } else {
                    acceptedBookings.add(key);
                    valid++;
                }

            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        System.out.println("Valid: " + valid
                + " | Rejected: " + rejected
                + " | Duplicates skipped: " + duplicates);
    }
}

public class Pb1 {

    public static void main(String[] args) {

        String[][] rawBookings = {
            {"Divya", "Chennai"},
            {"", "Bangalore"},
            {"Ravi123", "Pune"},
            {"Divya", "Chennai"},
            {" ", " "}
        };

        BusTicket.processBatch(rawBookings);
    }
}
