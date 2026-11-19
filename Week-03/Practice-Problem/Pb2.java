
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

class ScholarshipFeeAccount extends FeeAccount {
    private final double scholarshipPercent;

    ScholarshipFeeAccount(String regNo, double totalFee,
                          double amountPaid, double scholarshipPercent) {
        super(regNo, totalFee, amountPaid);

        if (scholarshipPercent >= 0 && scholarshipPercent <= 100) {
            this.scholarshipPercent = scholarshipPercent;
        } else {
            this.scholarshipPercent = 0;
        }
    }

    double effectiveDue() {
        return getDue() - (getDue() * scholarshipPercent / 100);
    }
}

public class Pb2 {
    public static void main(String[] args) {

        FeeAccount plain = new FeeAccount("101", 150000, 0);

        HostelFeeAccount hostel =
                new HostelFeeAccount("102", 200000, 0);

        ScholarshipFeeAccount scholarship =
                new ScholarshipFeeAccount("103", 180000, 0, 20);

        FeeAccount[] accounts = {plain, hostel, scholarship};

        plain.pay(150000);

        for (FeeAccount account : accounts) {

            if (account instanceof HostelFeeAccount hostelAccount) {
                hostelAccount.payInTwoInstallments(60000);
            }

            if (account instanceof ScholarshipFeeAccount scholarshipAccount) {
                System.out.println("Scholarship effective due: "
                        + scholarshipAccount.effectiveDue());
            }
        }

        System.out.println("Plain account due: " + plain.getDue());
        System.out.println("Hostel account due: " + hostel.getDue());

        if (accounts[2] instanceof ScholarshipFeeAccount scholarshipAccount) {
            System.out.println("Scholarship account due: "
                    + scholarshipAccount.effectiveDue());
        }
    }
}


