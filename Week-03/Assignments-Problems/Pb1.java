
class BookIssue {

    String title;
    String borrowerName;
    int daysOverdue;

    // Constructor
    BookIssue(String title, String borrowerName, int daysOverdue) {
        this.title = title;
        this.borrowerName = borrowerName;
        this.daysOverdue = daysOverdue;
    }

    // Calculates fine for an individual book
    double fineAmount() {
        if (daysOverdue > 0) {
            return daysOverdue * 5;
        } else {
            return 0;
        }
    }

    // Checks whether the book is overdue by more than 14 days
    boolean isSeverelyOverdue() {
        return daysOverdue > 14;
    }

    // Calculates total fine collected from all books
    // Static because it operates on an array of BookIssue objects,
    // not on one particular book.
    static double totalFineCollected(BookIssue[] issues) {
        double total = 0;

        for (BookIssue book : issues) {
            total += book.fineAmount();
        }

        return total;
    }
}

public class Pb1 {

    public static void main(String[] args) {

        BookIssue[] issues = {
            new BookIssue("Clean Code", "Ravi", 18),
            new BookIssue("Effective Java", "Meera", 5),
            new BookIssue("Refactoring", "Arun", 0),
            new BookIssue("DSA Handbook", "Priya", 21),
            new BookIssue("Design Patterns", "Rahul", 9)
        };

        for (BookIssue book : issues) {

            System.out.print(book.title + " - "
                    + book.daysOverdue + " days - ");

            if (book.isSeverelyOverdue()) {
                System.out.println("Severely overdue");
            } else {
                System.out.println("OK");
            }
        }

        double total = BookIssue.totalFineCollected(issues);

        System.out.println("Total fine collected: Rs " + total);
    }
}
