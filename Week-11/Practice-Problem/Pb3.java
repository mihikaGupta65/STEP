
class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

public class Pb3 {

    public static Node addNumbers(Node a, Node b) {

        Node dummy = new Node(0);
        Node tail = dummy;

        int carry = 0;

        while (a != null || b != null || carry != 0) {

            int sum = carry;

            if (a != null) {
                sum += a.data;
                a = a.next;
            }

            if (b != null) {
                sum += b.data;
                b = b.next;
            }

            carry = sum / 10;

            tail.next = new Node(sum % 10);
            tail = tail.next;
        }

        return dummy.next;
    }

    public static void display(Node head) {
        Node current = head;

        while (current != null) {
            System.out.print(current.data);

            if (current.next != null) {
                System.out.print(" -> ");
            }

            current = current.next;
        }

        System.out.println();
    }

    public static void main(String[] args) {

        Node a = new Node(2);
        a.next = new Node(4);
        a.next.next = new Node(3);

        Node b = new Node(5);
        b.next = new Node(6);
        b.next.next = new Node(4);

        Node result = addNumbers(a, b);

        System.out.println("First Sum:");
        display(result);

        Node x = new Node(9);
        x.next = new Node(9);

        Node y = new Node(1);

        Node result2 = addNumbers(x, y);

        System.out.println("Second Sum:");
        display(result2);
    }
}
