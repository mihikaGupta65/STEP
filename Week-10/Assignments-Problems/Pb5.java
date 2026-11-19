
class Node {
    int coefficient;
    int exponent;
    Node next;

    Node(int coefficient, int exponent) {
        this.coefficient = coefficient;
        this.exponent = exponent;
        this.next = null;
    }
}

public class Pb5 {

    public static Node addPolynomials(Node p, Node q) {

        Node dummy = new Node(0, 0);
        Node tail = dummy;

        while (p != null && q != null) {

            if (p.exponent > q.exponent) {
                tail.next = new Node(p.coefficient, p.exponent);
                p = p.next;
            } 
            else if (q.exponent > p.exponent) {
                tail.next = new Node(q.coefficient, q.exponent);
                q = q.next;
            } 
            else {
                int sum = p.coefficient + q.coefficient;

                if (sum != 0) {
                    tail.next = new Node(sum, p.exponent);
                }

                p = p.next;
                q = q.next;

                if (sum == 0) {
                    continue;
                }
            }

            if (tail.next != null) {
                tail = tail.next;
            }
        }

        while (p != null) {
            tail.next = new Node(p.coefficient, p.exponent);
            tail = tail.next;
            p = p.next;
        }

        while (q != null) {
            tail.next = new Node(q.coefficient, q.exponent);
            tail = tail.next;
            q = q.next;
        }

        return dummy.next;
    }

    public static void printPolynomial(Node head) {

        if (head == null) {
            System.out.println("0");
            return;
        }

        Node current = head;
        boolean first = true;

        while (current != null) {

            int c = current.coefficient;
            int e = current.exponent;

            if (!first) {
                if (c > 0) {
                    System.out.print(" + ");
                } else {
                    System.out.print(" - ");
                }
            } else if (c < 0) {
                System.out.print("-");
            }

            int absCoefficient = Math.abs(c);

            if (e == 0 || absCoefficient != 1) {
                System.out.print(absCoefficient);
            }

            if (e > 0) {
                System.out.print("x");

                if (e > 1) {
                    System.out.print("^" + e);
                }
            }

            first = false;
            current = current.next;
        }

        System.out.println();
    }

    public static void main(String[] args) {

        // First polynomial: 5x^3 + 4x^2 + 2
        Node p = new Node(5, 3);
        p.next = new Node(4, 2);
        p.next.next = new Node(2, 0);

        // Second polynomial: 5x^2 - 3x + 1
        Node q = new Node(5, 2);
        q.next = new Node(-3, 1);
        q.next.next = new Node(1, 0);

        Node result = addPolynomials(p, q);

        System.out.print("Sum: ");
        printPolynomial(result);

        // Second example
        Node a = new Node(3, 2);
        a.next = new Node(1, 0);

        Node b = new Node(-3, 2);
        b.next = new Node(4, 1);

        Node result2 = addPolynomials(a, b);

        System.out.print("Second Sum: ");
        printPolynomial(result2);
    }
}
