
import java.util.Scanner;

public class Pb1 {

    static class Order {

        static class LineItem {
            String productName;
            int quantity;
            double unitPrice;

            LineItem(String productName, int quantity, double unitPrice) {
                this.productName = productName;
                this.quantity = quantity;
                this.unitPrice = unitPrice;
            }

            double total() {
                return quantity * unitPrice;
            }
        }

        private LineItem[] items;
        private int count;

        Order(int capacity) {
            items = new LineItem[capacity];
            count = 0;
        }

        void addItem(String name, int quantity, double price) {
            if (count < items.length) {
                items[count] = new LineItem(name, quantity, price);
                count++;
            }
        }

        class Cursor {
            private int index = 0;

            boolean hasNext() {
                return index < count;
            }

            LineItem next() {
                if (hasNext()) {
                    return items[index++];
                }
                return null;
            }

            void reset() {
                index = 0;
            }
        }

        double orderTotal() {
            double total = 0;

            for (int i = 0; i < count; i++) {
                total += items[i].total();
            }

            return total;
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Order order = new Order(100);

        System.out.print("Enter number of line items: ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            String product = sc.next();
            int quantity = sc.nextInt();
            double price = sc.nextDouble();

            order.addItem(product, quantity, price);
        }

        Order.Cursor cursor = order.new Cursor();

        while (cursor.hasNext()) {
            Order.LineItem item = cursor.next();

            System.out.println(item.productName + " " + item.total());
        }

        System.out.println("Total " + order.orderTotal());

        System.out.println("cursor: inner (reads the order's array)");
        System.out.println("line item: static nested (needs no order)");
    }
}
