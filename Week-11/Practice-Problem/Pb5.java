
class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

public class Pb5 {

    public static Node reverse(Node head) {

        Node prev = null;
        Node current = head;

        while (current != null) {
            Node nextNode = current.next;
            current.next = prev;
            prev = current;
            current = nextNode;
        }

        return prev;
    }

    public static void reorderList(Node head) {

        if (head == null || head.next == null) {
            return;
        }

        // Find the middle
        Node slow = head;
        Node fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Split the list into two halves
        Node secondHalf = slow.next;
        slow.next = null;

        // Reverse the second half
        secondHalf = reverse(secondHalf);

        // Merge both halves alternately
        Node first = head;
        Node second = secondHalf;

        while (second != null) {

            Node nextFirst = first.next;
            Node nextSecond = second.next;

            first.next = second;
            second.next = nextFirst;

            first = nextFirst;
            second = nextSecond;
        }
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

        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);

        System.out.println("Original List:");
        display(head);

        reorderList(head);

        System.out.println("Reordered List:");
        display(head);

        Node second = new Node(1);
        second.next = new Node(2);
        second.next.next = new Node(3);
        second.next.next.next = new Node(4);
        second.next.next.next.next = new Node(5);

        System.out.println("Original List:");
        display(second);

        reorderList(second);

        System.out.println("Reordered List:");
        display(second);
    }
}
