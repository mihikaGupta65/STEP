
import java.util.HashSet;

class Customer {
    private int id;
    private String name;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Customer)) {
            return false;
        }

        Customer other = (Customer) obj;

        return id == other.id && name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return 31 * id + name.hashCode();
    }
}

public class Pb2 {

    public static void main(String[] args) {
        HashSet<Customer> customers = new HashSet<>();

        Customer c1 = new Customer(101, "Asha");
        Customer c2 = new Customer(101, "Asha");
        Customer c3 = new Customer(102, "Ravi");

        System.out.println(customers.add(c1));

        if (!customers.add(c2)) {
            System.out.println("false (duplicate rejected)");
        }

        System.out.println(customers.add(c3));

        System.out.println("unique count " + customers.size());

        System.out.println("contains: " + customers.contains(
                new Customer(101, "Asha")));
    }
}
