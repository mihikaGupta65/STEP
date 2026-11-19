
class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

public class Pb4 {

    public static Node findIntersection(Node a, Node b) {

        int lengthA = 0;
        int lengthB = 0;

        Node tempA = a;
        Node tempB = b;

        while (tempA != null) {
            lengthA++;
            tempA = tempA.next;
        }

        while (tempB != null) {
            lengthB++;
            tempB = tempB.next;
        }

        Node p = a;
        Node q = b;

        if (lengthA > lengthB) {
            for (int i = 0; i < lengthA - lengthB; i++) {
                p = p.next;
            }
        } else {
            for (int i = 0; i < lengthB - lengthA; i++) {
                q = q.next;
            }
        }

        while (p != null && q != null) {

            if (p == q) {
                return p;
            }

            p = p.next;
            q = q.next;
        }

        return null;
    }

    public static void main(String[] args) {

        Node shared = new Node(8);
        shared.next = new Node(4);
        shared.next.next = new Node(5);

        Node a = new Node(4);
        a.next = new Node(1);
        a.next.next = shared;

        Node b = new Node(5);
        b.next = new Node(6);
        b.next.next = new Node(1);
        b.next.next.next = shared;

        Node result = findIntersection(a, b);

        if (result != null) {
            System.out.println("Intersection Node: " + result.data);
        } else {
            System.out.println("No Intersection");
        }

        Node x = new Node(2);
        x.next = new Node(6);
        x.next.next = new Node(4);

        Node y = new Node(1);
        y.next = new Node(5);

        Node result2 = findIntersection(x, y);

        if (result2 != null) {
            System.out.println("Intersection Node: " + result2.data);
        } else {
            System.out.println("No Intersection");
        }
    }
}
