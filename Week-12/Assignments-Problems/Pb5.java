
import java.util.Scanner;

class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

public class Pb5 {

    // Reverse a linked list
    private static Node reverse(Node head) {

        Node prev = null;
        Node curr = head;

        while (curr != null) {

            Node nextNode = curr.next;

            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }

        return prev;
    }

    public static boolean isPalindrome(Node head) {

        if (head == null || head.next == null) {
            return true;
        }

        // Find the end of the first half
        Node slow = head;
        Node fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Reverse the second half
        Node secondHalf = reverse(slow.next);

        // Compare both halves
        Node first = head;
        Node second = secondHalf;

        boolean result = true;

        while (second != null) {

            if (first.data != second.data) {
                result = false;
                break;
            }

            first = first.next;
            second = second.next;
        }

        // Restore the original linked list
        slow.next = reverse(secondHalf);

        return result;
    }

    // Print linked list
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

        System.out.print("Enter number of tokens: ");
        int n = sc.nextInt();

        Node head = null;
        Node tail = null;

        System.out.println("Enter token numbers:");

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

        boolean result = isPalindrome(head);

        System.out.println("Palindrome: " + result);

        System.out.println("List after check:");
        printList(head);

        sc.close();
    }
}
