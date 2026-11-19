
import java.util.Scanner;

class Node {
    String title;
    int dueDay;
    Node next;

    Node(String title, int dueDay) {
        this.title = title;
        this.dueDay = dueDay;
        this.next = null;
    }
}

public class Pb4 {

    public static Node sortByDueDay(Node head) {

        // Base case
        if (head == null || head.next == null) {
            return head;
        }

        // Find the middle of the list
        Node slow = head;
        Node fast = head;
        Node prev = null;

        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        // Split the list into two halves
        prev.next = null;

        // Sort both halves
        Node left = sortByDueDay(head);
        Node right = sortByDueDay(slow);

        // Merge the sorted halves
        return merge(left, right);
    }

    private static Node merge(Node left, Node right) {

        Node dummy = new Node("", 0);
        Node tail = dummy;

        while (left != null && right != null) {

            // Take left node on ties to maintain stability
            if (left.dueDay <= right.dueDay) {

                tail.next = left;
                left = left.next;

            } else {

                tail.next = right;
                right = right.next;
            }

            tail = tail.next;
        }

        // Attach remaining nodes
        if (left != null) {
            tail.next = left;
        } else {
            tail.next = right;
        }

        return dummy.next;
    }

    public static void printList(Node head) {

        Node temp = head;

        while (temp != null) {
            System.out.print(temp.title + ":" + temp.dueDay + " -> ");
            temp = temp.next;
        }

        System.out.println("null");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of returned books: ");
        int n = sc.nextInt();

        Node head = null;
        Node tail = null;

        System.out.println("Enter title and due day:");

        for (int i = 0; i < n; i++) {

            String title = sc.next();
            int day = sc.nextInt();

            Node newNode = new Node(title, day);

            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
        }

        head = sortByDueDay(head);

        System.out.println("Sorted returns:");
        printList(head);

        sc.close();
    }
}
