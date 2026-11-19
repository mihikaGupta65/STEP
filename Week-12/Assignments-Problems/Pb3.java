
import java.util.HashMap;
import java.util.Scanner;

class Node {
    int key;
    String page;
    Node prev;
    Node next;

    Node(int key, String page) {
        this.key = key;
        this.page = page;
    }
}

class RecentCache {

    private int capacity;
    private HashMap<Integer, Node> map;

    private Node head;
    private Node tail;

    RecentCache(int capacity) {

        this.capacity = capacity;
        map = new HashMap<>();

        head = new Node(0, "");
        tail = new Node(0, "");

        head.next = tail;
        tail.prev = head;
    }

    // Remove a node from the linked list
    private void remove(Node node) {

        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // Insert a node immediately after head
    private void addToFront(Node node) {

        node.next = head.next;
        node.prev = head;

        head.next.prev = node;
        head.next = node;
    }

    // Move an existing node to the most recent position
    private void moveToFront(Node node) {

        remove(node);
        addToFront(node);
    }

    public String get(int key) {

        if (!map.containsKey(key)) {
            return "-1";
        }

        Node node = map.get(key);

        moveToFront(node);

        return node.page;
    }

    public String put(int key, String page) {

        // Update an existing page
        if (map.containsKey(key)) {

            Node node = map.get(key);
            node.page = page;

            moveToFront(node);

            return "updated";
        }

        String result = "added";

        // Remove least recently used page if cache is full
        if (map.size() == capacity) {

            Node lru = tail.prev;

            remove(lru);
            map.remove(lru.key);

            result = "evicted " + lru.key;
        }

        // Add new page at the front
        Node newNode = new Node(key, page);

        addToFront(newNode);
        map.put(key, newNode);

        return result;
    }

    public void display() {

        System.out.print("Cache (most recent first): ");

        Node temp = head.next;

        while (temp != tail) {

            System.out.print(temp.key + "=" + temp.page);

            temp = temp.next;

            if (temp != tail) {
                System.out.print(", ");
            }
        }

        System.out.println();
    }
}

public class Pb3 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter cache capacity: ");
        int capacity = sc.nextInt();

        RecentCache cache = new RecentCache(capacity);

        System.out.print("Enter number of operations: ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {

            String operation = sc.next();

            if (operation.equals("put")) {

                int key = sc.nextInt();
                String page = sc.next();

                String result = cache.put(key, page);

                System.out.print("put(" + key + "," + page + ")");

                if (result.equals("updated")) {
                    System.out.println(" updated");
                } else if (result.startsWith("evicted")) {
                    System.out.println(" " + result);
                } else {
                    System.out.println();
                }
            }

            else if (operation.equals("get")) {

                int key = sc.nextInt();

                String result = cache.get(key);

                System.out.println("get(" + key + ") -> " + result);
            }
        }

        cache.display();

        sc.close();
    }
}
