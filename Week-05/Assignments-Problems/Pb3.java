
class BookInventory {

    private int copiesTotal;
    private int copiesAvailable;

    public BookInventory(int copiesTotal) {

        if (copiesTotal <= 0 || copiesTotal > 500) {
            throw new IllegalArgumentException("Invalid inventory count");
        }

        this.copiesTotal = copiesTotal;
        this.copiesAvailable = copiesTotal;
    }

    public void checkOut() {

        if (copiesAvailable > 0) {
            copiesAvailable--;
        }
    }

    public void checkIn() {

        if (copiesAvailable < copiesTotal) {
            copiesAvailable++;
        }
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }
}

public class Pb3 {

    public static void main(String[] args) {

        try {
            BookInventory b = new BookInventory(0);
            System.out.println(b.getCopiesAvailable());
        } catch (IllegalArgumentException e) {
            System.out.println("Construction rejected");
        }

        BookInventory b = new BookInventory(3);

        b.checkOut();
        b.checkOut();
        b.checkOut();
        b.checkOut();

        System.out.println("Available after checkout: " +
                           b.getCopiesAvailable());

        b.checkIn();
        b.checkIn();
        b.checkIn();
        b.checkIn();

        System.out.println("Available after check-in: " +
                           b.getCopiesAvailable());
    }
}
