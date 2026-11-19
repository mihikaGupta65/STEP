
public class Pb1 {

    String name;
    String regNo;
    int attendance;

    Pb1(String name, String regNo, int attendance) {
        this.name = name;
        this.regNo = regNo;
        this.attendance = attendance;
    }

    void addAttendanceUpdate(int newAttendance) {
        attendance = newAttendance;
    }

    boolean isEligible() {
        return attendance >= 75;
    }

    // Static because classAverage calculates the average for all students.
    // isEligible is not static because it checks an individual student's attendance.
    static double classAverage(Pb1[] students) {
        int total = 0;

        for (Pb1 student : students) {
            total += student.attendance;
        }

        return (double) total / students.length;
    }

    public static void main(String[] args) {

        Pb1[] students = new Pb1[5];

        students[0] = new Pb1("Ravi", "101", 82);
        students[1] = new Pb1("Anitha", "102", 68);
        students[2] = new Pb1("Karthik", "103", 91);
        students[3] = new Pb1("Meera", "104", 74);
        students[4] = new Pb1("Suresh", "105", 60);

        // Example of updating attendance after re-check
        students[0].addAttendanceUpdate(82);

        for (Pb1 student : students) {

            if (student.isEligible()) {
                System.out.println(student.name + " - "
                        + student.regNo + " - "
                        + student.attendance + "% - Eligible");
            } else {
                System.out.println(student.name + " - "
                        + student.regNo + " - "
                        + student.attendance + "% - Detained");
            }
        }

        System.out.println("Class average: "
                + Pb1.classAverage(students) + "%");
    }
}

