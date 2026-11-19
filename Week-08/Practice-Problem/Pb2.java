
import java.util.ArrayList;
import java.util.List;

abstract class Vehicle {
    private String vehicleName;
    private boolean available;

    public Vehicle(String vehicleName) {
        this.vehicleName = vehicleName;
        this.available = true;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public abstract double calculateRentalCharge(int days);
}

class StandardCar extends Vehicle {
    public StandardCar(String vehicleName) {
        super(vehicleName);
    }

    @Override
    public double calculateRentalCharge(int days) {
        return days * 50.0;
    }
}

class LuxuryCar extends Vehicle {
    public LuxuryCar(String vehicleName) {
        super(vehicleName);
    }

    @Override
    public double calculateRentalCharge(int days) {
        return days * 100.0;
    }
}

class SUV extends Vehicle {
    public SUV(String vehicleName) {
        super(vehicleName);
    }

    @Override
    public double calculateRentalCharge(int days) {
        return days * 80.0;
    }
}

class Customer {
    private String customerName;

    public Customer(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }
}

class Rental {
    private Customer customer;
    private Vehicle vehicle;
    private int days;
    private double totalCharge;
    private boolean active;

    public Rental(Customer customer, Vehicle vehicle, int days) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.days = days;
        this.totalCharge = vehicle.calculateRentalCharge(days);
        this.active = true;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public boolean isActive() {
        return active;
    }

    public void closeRental() {
        active = false;
        vehicle.setAvailable(true);
    }

    public double getTotalCharge() {
        return totalCharge;
    }
}

class RentalService {
    private List<Vehicle> vehicles;
    private List<Rental> rentals;

    public RentalService() {
        vehicles = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void rentVehicle(Customer customer, String vehicleName, int days) {
        if (days <= 0) {
            System.out.println("Rental duration must be positive.");
            return;
        }

        Vehicle selectedVehicle = null;

        for (Vehicle vehicle : vehicles) {
            if (vehicle.getVehicleName().equalsIgnoreCase(vehicleName)) {
                selectedVehicle = vehicle;
                break;
            }
        }

        if (selectedVehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        if (!selectedVehicle.isAvailable()) {
            System.out.println(vehicleName + " is already rented.");
            return;
        }

        Rental rental = new Rental(customer, selectedVehicle, days);

        selectedVehicle.setAvailable(false);
        rentals.add(rental);

        System.out.printf("%s rented for %d days. Total charge: $%.2f%n",
                vehicleName, days, rental.getTotalCharge());
    }

    public void returnVehicle(String vehicleName) {
        for (Rental rental : rentals) {
            if (rental.getVehicle().getVehicleName()
                    .equalsIgnoreCase(vehicleName)
                    && rental.isActive()) {

                rental.closeRental();

                System.out.println(vehicleName + " returned. Now available.");
                return;
            }
        }

        System.out.println("No active rental found for " + vehicleName + ".");
    }
}

public class Pb2 {
    public static void main(String[] args) {

        RentalService service = new RentalService();

        Customer customer = new Customer("Rahul");

        Vehicle luxury = new LuxuryCar("Luxury Car A");
        Vehicle standard = new StandardCar("Standard Car B");
        Vehicle suv = new SUV("SUV C");

        service.addVehicle(luxury);
        service.addVehicle(standard);
        service.addVehicle(suv);

        service.rentVehicle(customer, "Luxury Car A", 3);

        service.rentVehicle(customer, "Standard Car B", 5);

        System.out.println();

        service.returnVehicle("Luxury Car A");

        System.out.println();

        service.rentVehicle(customer, "Luxury Car A", 2);
    }
}
