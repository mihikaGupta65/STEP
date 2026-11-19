
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

interface PricingStrategy {
    double calculatePrice(long days);
}

class StandardPricing implements PricingStrategy {
    @Override
    public double calculatePrice(long days) {
        return days * 150.0;
    }
}

class DeluxePricing implements PricingStrategy {
    @Override
    public double calculatePrice(long days) {
        return days * 200.0;
    }
}

class SuitePricing implements PricingStrategy {
    @Override
    public double calculatePrice(long days) {
        return days * 300.0;
    }
}

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Room {
    private String roomName;
    private PricingStrategy pricingStrategy;

    public Room(String roomName, PricingStrategy pricingStrategy) {
        this.roomName = roomName;
        this.pricingStrategy = pricingStrategy;
    }

    public String getRoomName() {
        return roomName;
    }

    public double calculatePrice(long days) {
        return pricingStrategy.calculatePrice(days);
    }
}

class Reservation {
    private Customer customer;
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private LocalDate cancellationDeadline;
    private boolean active;
    private double totalPrice;

    public Reservation(Customer customer, Room room,
                       LocalDate checkIn, LocalDate checkOut,
                       LocalDate cancellationDeadline) {
        this.customer = customer;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.cancellationDeadline = cancellationDeadline;
        this.active = true;

        long days = ChronoUnit.DAYS.between(checkIn, checkOut);
        this.totalPrice = room.calculatePrice(days);
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public boolean isActive() {
        return active;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean overlaps(LocalDate start, LocalDate end) {
        return checkIn.isBefore(end) && start.isBefore(checkOut);
    }

    public boolean cancel(LocalDate today) {
        if (!active) {
            return false;
        }

        if (!today.isBefore(cancellationDeadline)) {
            return false;
        }

        active = false;
        return true;
    }
}

class BookingManager {
    private List<Room> rooms;
    private List<Reservation> reservations;

    public BookingManager() {
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void bookRoom(Customer customer, String roomName,
                         LocalDate checkIn, LocalDate checkOut,
                         LocalDate cancellationDeadline) {

        if (checkIn == null || checkOut == null
                || !checkIn.isBefore(checkOut)) {
            System.out.println("Invalid booking dates.");
            return;
        }

        Room selectedRoom = null;

        for (Room room : rooms) {
            if (room.getRoomName().equalsIgnoreCase(roomName)) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Room not found: " + roomName);
            return;
        }

        for (Reservation reservation : reservations) {
            if (reservation.isActive()
                    && reservation.getRoom() == selectedRoom
                    && reservation.overlaps(checkIn, checkOut)) {

                System.out.println("Booking failed: " + roomName
                        + " is not available for "
                        + checkIn + " to " + checkOut + ".");
                return;
            }
        }

        if (cancellationDeadline == null
                || !cancellationDeadline.isBefore(checkIn)) {
            System.out.println("Invalid cancellation deadline.");
            return;
        }

        Reservation reservation = new Reservation(
                customer, selectedRoom, checkIn,
                checkOut, cancellationDeadline);

        reservations.add(reservation);

        System.out.printf("%s booked from %s to %s. Total price: $%.2f%n",
                roomName, checkIn, checkOut,
                reservation.getTotalPrice());
    }

    public void cancelReservation(String roomName, LocalDate today) {
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().getRoomName()
                    .equalsIgnoreCase(roomName)
                    && reservation.isActive()) {

                if (reservation.cancel(today)) {
                    System.out.println("Reservation for " + roomName
                            + " cancelled successfully.");
                } else {
                    System.out.println("Cancellation failed: deadline passed.");
                }
                return;
            }
        }

        System.out.println("No active reservation found for " + roomName);
    }
}

public class Pb3 {
    public static void main(String[] args) {

        BookingManager manager = new BookingManager();

        manager.addRoom(new Room(
                "Deluxe Room 101", new DeluxePricing()));

        manager.addRoom(new Room(
                "Standard Room 205", new StandardPricing()));

        manager.addRoom(new Room(
                "Suite Room 301", new SuitePricing()));

        Customer customer = new Customer("Rahul");

        manager.bookRoom(
                customer,
                "Deluxe Room 101",
                LocalDate.of(2024, 12, 1),
                LocalDate.of(2024, 12, 5),
                LocalDate.of(2024, 11, 28));

        manager.bookRoom(
                customer,
                "Standard Room 205",
                LocalDate.of(2024, 12, 3),
                LocalDate.of(2024, 12, 7),
                LocalDate.of(2024, 11, 30));

        manager.bookRoom(
                customer,
                "Deluxe Room 101",
                LocalDate.of(2024, 12, 3),
                LocalDate.of(2024, 12, 7),
                LocalDate.of(2024, 11, 30));

        System.out.println();

        manager.cancelReservation(
                "Deluxe Room 101",
                LocalDate.of(2024, 11, 20));
    }
}
