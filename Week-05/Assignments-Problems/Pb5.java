
import java.util.Arrays;

class LoanReceipt {

    private final String memberId;
    private final String[] bookIds;

    public LoanReceipt(String memberId, String[] bookIds) {

        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid member ID");
        }

        if (bookIds == null || bookIds.length > 20) {
            throw new IllegalArgumentException("Invalid book array");
        }

        for (String id : bookIds) {
            if (id == null || !id.matches("BK-[0-9]{3}")) {
                throw new IllegalArgumentException("Invalid book ID");
            }
        }

        this.memberId = memberId;
        this.bookIds = Arrays.copyOf(bookIds, bookIds.length);
    }

    public String getMemberId() {
        return memberId;
    }

    public String[] getBookIds() {
        return Arrays.copyOf(bookIds, bookIds.length);
    }

    public LoanReceipt withCorrectedBookId(int index, String newId) {

        if (index < 0 || index >= bookIds.length) {
            throw new IndexOutOfBoundsException("Invalid book index");
        }

        if (newId == null || !newId.matches("BK-[0-9]{3}")) {
            throw new IllegalArgumentException("Invalid book ID");
        }

        String[] correctedIds = getBookIds();
        correctedIds[index] = newId;

        return new LoanReceipt(memberId, correctedIds);
    }
}

class ReferenceOnlyLoanReceipt extends LoanReceipt {

    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds,
                                    String roomNumber) {

        super(memberId, bookIds);

        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid room number");
        }

        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}

public class Pb5 {

    private static int nightlyProcessed;

    static {
        nightlyProcessed = 0;
    }

    static String processNightlyCirculation(LoanReceipt[] receipts) {

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        if (receipts == null) {
            return "0 processed | 0 null skipped | 0 reference-only | 0 regular";
        }

        for (LoanReceipt receipt : receipts) {

            if (receipt == null) {
                nullSkipped++;
                continue;
            }

            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }

            processed++;
        }

        nightlyProcessed += processed;

        return processed + " processed | " +
               nullSkipped + " null skipped | " +
               referenceOnly + " reference-only | " +
               regular + " regular";
    }

    public static void main(String[] args) {

        try {
            LoanReceipt invalid = new LoanReceipt(
                    "LIB-8841",
                    new String[]{"BK-100", "bad"}
            );

            System.out.println("Receipt created");

        } catch (IllegalArgumentException e) {
            System.out.println("Construction rejected");
        }

        LoanReceipt r = new LoanReceipt(
                "LIB-8841",
                new String[]{"BK-100", "BK-101"}
        );

        String[] ids = r.getBookIds();
        ids[0] = "HACKED";

        System.out.println("Original book ID: " + r.getBookIds()[0]);

        LoanReceipt corrected = r.withCorrectedBookId(0, "BK-999");

        System.out.println("Corrected book ID: " + corrected.getBookIds()[0]);
        System.out.println("Original receipt ID: " + r.getBookIds()[0]);

        LoanReceipt[] batch = {
                new ReferenceOnlyLoanReceipt(
                        "LIB-001",
                        new String[]{"BK-200"},
                        "Reading Room 3"
                ),
                null,
                new LoanReceipt(
                        "LIB-002",
                        new String[]{"BK-201"}
                )
        };

        System.out.println(processNightlyCirculation(batch));
    }
}
