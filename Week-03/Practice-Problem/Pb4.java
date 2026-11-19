
class BrokenSrmStudent {

    static String name;
    static String regNo;
    static int attendance;

    BrokenSrmStudent(String name, String regNo, int attendance) {
        BrokenSrmStudent.name = name;
        BrokenSrmStudent.regNo = regNo;
        BrokenSrmStudent.attendance = attendance;
    }

    void printDetails() {
        System.out.println(name + " | " + regNo + " | " + attendance);
    }
}

class SrmStudent {

    String name;
    String regNo;
    int attendance;

    static String university = "SRM";
    static int admissionCount = 0;

    SrmStudent(String name, int attendance) {
        this.name = name;
        this.attendance = attendance;

        admissionCount++;
        this.regNo = "RA23110030101" + admissionCount;
    }

    void printIdCard() {
        System.out.println(university + " | " + name + " | "
                + regNo + " | " + attendance);
    }

    static void printTotalAdmissions() {
        System.out.println("Total Admissions: " + admissionCount);
    }
}

public class Pb4 {

    public static void main(String[] args) {

        System.out.println("Broken Design:");

        // Static fields are shared by all objects.
        // Creating s2 overwrites the values previously stored by s1.

        BrokenSrmStudent s1 =
                new BrokenSrmStudent("Ravi", "101", 80);

        BrokenSrmStudent s2 =
                new BrokenSrmStudent("Meera", "102", 90);

        s1.printDetails();
        s2.printDetails();

        System.out.println();

        System.out.println("Corrected Design:");

        // Each student gets separate instance variables.
        // University and admissionCount are shared static variables.

        SrmStudent s3 = new SrmStudent("Ravi", 80);
        SrmStudent s4 = new SrmStudent("Meera", 90);

        s3.printIdCard();
        s4.printIdCard();

        SrmStudent.printTotalAdmissions();
    }
}

