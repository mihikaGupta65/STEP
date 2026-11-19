
import java.util.Scanner;

class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

public class Pb2 {

    public static Node reverseInGroups(Node head, int k) {

        if (head == null || k == 1) {
            return head;
        }

        Node dummy = new Node(0);
        dummy.next = head;

        Node groupPrev = dummy;

        while (true) {

            // Check whether k nodes are available
            Node kth = groupPrev;

            for (int i = 0; i < k && kth != null; i++) {
                kth = kth.next;
            }

            // Fewer than k nodes remain
            if (kth == null) {
                break;
            }

            Node groupNext = kth.next;

            // Reverse the current group
            Node prev = groupNext;
            Node curr = groupPrev.next;

            while (curr != groupNext) {

                Node nextNode = curr.next;

                curr.next = prev;
                prev = curr;
                curr = nextNode;
            }

            // Connect the reversed group
            Node oldGroupHead = groupPrev.next;

            groupPrev.next = kth;
            groupPrev = oldGroupHead;
        }

        return dummy.next;
    }

    public static void printList(Node head) {

        Node temp = head;

        while (temp != null) {
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }

        System.out.println("null");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of scripts: ");
        int n = sc.nextInt();

        Node head = null;
        Node tail = null;

        System.out.println("Enter script numbers:");

        for (int i = 0; i < n; i++) {

            int value = sc.nextInt();

            Node newNode = new Node(value);

            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
        }

        System.out.print("Enter batch size k: ");
        int k = sc.nextInt();

        head = reverseInGroups(head, k);

        System.out.println("Reversed list:");
        printList(head);

        sc.close();
    }
}
