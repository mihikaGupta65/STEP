
class FoodOrder {

    private String studentName;
    private String dishName;
    private boolean delivered;

    public FoodOrder(String studentName, String dishName) {

        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be empty");
        }

        if (dishName == null || dishName.trim().isEmpty()) {
            throw new IllegalArgumentException("Dish name cannot be empty");
        }

        this.studentName = studentName.trim();
        this.dishName = dishName.trim();
        this.delivered = false;
    }

    void markDelivered() {

        if (!delivered) {
            delivered = true;
            System.out.println("Order delivered to " + studentName);
        } else {
            System.out.println("Order already delivered to " + studentName);
        }
    }

    static void processBatch(String[][] rawOrders) {

        int accepted = 0;
        int rejected = 0;

        for (String[] order : rawOrders) {

            try {
                if (order == null || order.length < 2) {
                    throw new IllegalArgumentException("Invalid order");
                }

                FoodOrder foodOrder = new FoodOrder(order[0], order[1]);
                accepted++;

            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        System.out.println("Valid: " + accepted + " | Rejected: " + rejected);
    }
}

public class Pb1 {

    public static void main(String[] args) {

        String[][] rawOrders = {
            {"Ravi", "Paneer Butter Masala"},
            {"", "Chole Bhature"},
            {"Meera", " "},
            {"Divya", "Veg Biryani"}
        };

        FoodOrder.processBatch(rawOrders);

        FoodOrder order = new FoodOrder("Ravi", "Paneer Butter Masala");

        order.markDelivered();
        order.markDelivered();
    }
}
