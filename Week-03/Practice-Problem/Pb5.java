

class FeeAccount {
    private final String regNo;
    private final double totalFee;
    private double amountPaid;

    FeeAccount(String regNo, double totalFee, double amountPaid) {
        this.regNo = regNo;
        this.totalFee = totalFee;
        this.amountPaid = amountPaid;
    }

    void pay(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid payment amount");
        } else {
            amountPaid += amount;
        }
    }

    double getDue() {
        return totalFee - amountPaid;
    }

    String getRegNo() {
        return regNo;
    }
}

class HostelFeeAccount extends FeeAccount {

    HostelFeeAccount(String regNo, double totalFee, double amountPaid) {
        super(regNo, totalFee, amountPaid);
    }

    void payInTwoInstallments(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid payment amount");
            return;
        }

        pay(amount / 2);
        pay(amount / 2);
    }
}

class HostelRoom {
    String roomNo;
    int beds;
    int occupied;

    HostelRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    boolean allot(String name) {
        if (occupied < beds) {
            occupied++;
            return true;
        }

        return false;
    }

    static HostelRoom findAvailableRoom(HostelRoom[] rooms) {
        if (rooms == null) {
            return null;
        }

        for (HostelRoom room : rooms) {
            if (room != null && room.occupied < room.beds) {
                return room;
            }
        }

        return null;
    }

    static void safeAllot(HostelRoom[] rooms, SrmStudent student) {
        if (student == null) {
            return;
        }

        HostelRoom availableRoom = findAvailableRoom(rooms);

        if (availableRoom != null) {
            if (availableRoom.allot(student.name)) {
                student.room = availableRoom;
                System.out.println(student.name
                        + " allotted to room " + availableRoom.roomNo);
            }
        } else {
            System.out.println("No rooms available for " + student.name);
        }
    }
}

class SrmStudent {
    String name;
    String regNo;
    HostelFeeAccount feeAccount;
    HostelRoom room;

    static int totalStudents = 0;

    SrmStudent(String name, String regNo, HostelFeeAccount feeAccount) {
        this.name = name;
        this.regNo = regNo;
        this.feeAccount = feeAccount;
        this.room = null;

        totalStudents++;
    }

    void fullStatus() {
        System.out.print(regNo + " | " + name);
        System.out.print(" | Due: Rs " + feeAccount.getDue());

        if (room != null) {
            System.out.println(" | Room: " + room.roomNo);
        } else {
            System.out.println(" | Room: unallotted");
        }
    }
}

public class Pb5 {

    public static void main(String[] args) {

        HostelFeeAccount fee1 =
                new HostelFeeAccount("101", 200000, 0);

        HostelFeeAccount fee2 =
                new HostelFeeAccount("102", 180000, 0);

        HostelFeeAccount fee3 =
                new HostelFeeAccount("103", 200000, 0);

        SrmStudent s1 = new SrmStudent("Ravi", "101", fee1);
        SrmStudent s2 = new SrmStudent("Anitha", "102", fee2);
        SrmStudent s3 = new SrmStudent("Karthik", "103", fee3);

        HostelRoom[] rooms = {
            new HostelRoom("C-214", 1, 0),
            new HostelRoom("C-507", 1, 0)
        };

        System.out.println("Room Allocation:");

        HostelRoom.safeAllot(rooms, s1);
        HostelRoom.safeAllot(rooms, s2);

        System.out.println();

        System.out.println("Fee Payments:");

        s1.feeAccount.payInTwoInstallments(60000);
        s2.feeAccount.pay(-5000);

        System.out.println();

        System.out.println("Student Details:");

        s1.fullStatus();
        s2.fullStatus();
        s3.fullStatus();

        System.out.println("Total students: " + SrmStudent.totalStudents);
    }
}
