
import java.util.Scanner;

class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

public class Pb1 {

    public static String detectLoop(Node head) {

        Node slow = head;
        Node fast = head;

        // Detect loop using slow and fast pointers
        while (fast != null && fast.next != null) {

            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {

                // Count the length of the loop
                int length = 1;
                Node temp = slow.next;

                while (temp != slow) {
                    length++;
                    temp = temp.next;
                }

                // Find the starting node of the loop
                Node p1 = head;
                Node p2 = slow;

                while (p1 != p2) {
                    p1 = p1.next;
                    p2 = p2.next;
                }

                return "Loop: yes, starts at stop "
                        + p1.data + ", length " + length;
            }
        }

        return "Loop: no";
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of stops: ");
        int n = sc.nextInt();

        Node head = null;
        Node tail = null;

        Node[] nodes = new Node[n];

        System.out.println("Enter stop numbers:");

        for (int i = 0; i < n; i++) {

            int value = sc.nextInt();

            Node newNode = new Node(value);
            nodes[i] = newNode;

            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
        }

        System.out.print("Enter pos: ");
        int pos = sc.nextInt();

        // Connect last node to the given position
        if (pos != -1) {
            tail.next = nodes[pos];
        }

        System.out.println(detectLoop(head));
    }
}
