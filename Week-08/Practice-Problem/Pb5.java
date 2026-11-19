
import java.util.ArrayList;
import java.util.List;

interface IPaymentMethod {
    boolean pay(double amount);
    String getPaymentName();
}

interface OrderObserver {
    void notifyCustomer(String message);
}

class Customer implements OrderObserver {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public void notifyCustomer(String message) {
        System.out.println("Notification: " + message);
    }
}

class FoodItem {
    private String name;
    private double price;

    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class LineItem {
    private FoodItem foodItem;
    private int quantity;

    public LineItem(FoodItem foodItem, int quantity) {
        this.foodItem = foodItem;
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return foodItem.getPrice() * quantity;
    }

    public String getItemName() {
        return foodItem.getName();
    }

    public int getQuantity() {
        return quantity;
    }
}

class Restaurant {
    private String name;
    private List<FoodItem> menu;

    public Restaurant(String name) {
        this.name = name;
        menu = new ArrayList<>();
    }

    public void addFoodItem(FoodItem item) {
        menu.add(item);
    }

    public String getName() {
        return name;
    }
}

class CreditCardPayment implements IPaymentMethod {
    @Override
    public boolean pay(double amount) {
        return true;
    }

    @Override
    public String getPaymentName() {
        return "Credit Card";
    }
}

class DigitalWalletPayment implements IPaymentMethod {
    @Override
    public boolean pay(double amount) {
        return false;
    }

    @Override
    public String getPaymentName() {
        return "Digital Wallet";
    }
}

class CashOnDeliveryPayment implements IPaymentMethod {
    @Override
    public boolean pay(double amount) {
        return true;
    }

    @Override
    public String getPaymentName() {
        return "Cash on Delivery";
    }
}

enum OrderStatus {
    CREATED, PENDING_PAYMENT, PAID
}

class Order {
    private static int orderCounter = 122;

    private int orderId;
    private Customer customer;
    private List<LineItem> items;
    private OrderStatus status;
    private boolean placed;

    public Order(Customer customer) {
        orderCounter++;
        orderId = orderCounter;
        this.customer = customer;
        items = new ArrayList<>();
        status = OrderStatus.CREATED;
        placed = false;

        System.out.println("Order created.");
    }

    public void addItem(FoodItem item, int quantity) {
        if (placed) {
            System.out.println("Cannot add items after placing the order.");
            return;
        }

        if (item == null || quantity <= 0) {
            System.out.println("Invalid item or quantity.");
            return;
        }

        items.add(new LineItem(item, quantity));

        System.out.println("Added " + item.getName()
                + " (Qty " + quantity + ")");
    }

    public double calculateTotal() {
        double total = 0;

        for (LineItem item : items) {
            total += item.getSubtotal();
        }

        return total;
    }

    public void placeOrder(IPaymentMethod paymentMethod) {
        if (items.isEmpty()) {
            System.out.println(
                    "Cannot place order: Order must contain at least one item.");
            return;
        }

        if (placed) {
            System.out.println("Order has already been placed.");
            return;
        }

        placed = true;
        status = OrderStatus.PENDING_PAYMENT;

        System.out.println("Order placed successfully.");

        boolean paymentSuccess = paymentMethod.pay(calculateTotal());

        if (paymentSuccess) {
            status = OrderStatus.PAID;

            System.out.println("Payment via "
                    + paymentMethod.getPaymentName() + " successful.");

            System.out.println("Order status: " + status);

            customer.notifyCustomer(
                    "Order #" + orderId + " placed and paid.");
        } else {
            System.out.println("Payment via "
                    + paymentMethod.getPaymentName() + " failed.");

            System.out.println("Order status: " + status);

            customer.notifyCustomer(
                    "Order #" + orderId + " placed, awaiting payment.");
        }
    }
}

public class Pb5 {
    public static void main(String[] args) {

        Customer customer = new Customer("Rahul");

        FoodItem pizza = new FoodItem("Pizza", 250.0);
        FoodItem soda = new FoodItem("Soda", 50.0);
        FoodItem burger = new FoodItem("Burger", 150.0);

        Order order1 = new Order(customer);

        order1.addItem(pizza, 2);
        order1.addItem(soda, 1);

        Order emptyOrder = new Order(customer);
        emptyOrder.placeOrder(new CreditCardPayment());

        order1.placeOrder(new CreditCardPayment());

        System.out.println();

        Order order2 = new Order(customer);

        order2.addItem(burger, 1);
        order2.placeOrder(new DigitalWalletPayment());
    }
}
