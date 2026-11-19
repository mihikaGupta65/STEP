
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

interface CreditPolicy {
    int getCreditLimit();
}

class RegularPolicy implements CreditPolicy {
    public int getCreditLimit() {
        return 24;
    }
}

class HonorsPolicy implements CreditPolicy {
    public int getCreditLimit() {
        return 28;
    }
}

class ExchangePolicy implements CreditPolicy {
    public int getCreditLimit() {
        return 20;
    }
}

class Student {
    private String name;
    private int currentCredits;
    private CreditPolicy policy;

    public Student(String name, int currentCredits, CreditPolicy policy) {
        this.name = name;
        this.currentCredits = currentCredits;
        this.policy = policy;
    }

    public String getName() {
        return name;
    }

    public int getCurrentCredits() {
        return currentCredits;
    }

    public int getCreditLimit() {
        return policy.getCreditLimit();
    }

    public boolean canEnroll(int credits) {
        return currentCredits + credits <= getCreditLimit();
    }

    public void addCredits(int credits) {
        currentCredits += credits;
    }

    public void removeCredits(int credits) {
        currentCredits -= credits;
    }

    public String getStudentType() {
        if (policy instanceof RegularPolicy) {
            return "Regular";
        } else if (policy instanceof HonorsPolicy) {
            return "Honors";
        }
        return "Exchange";
    }
}

class Elective {
    private String electiveName;
    private int credits;
    private int capacity;
    private List<Student> enrolledStudents;
    private Queue<Student> waitlist;

    public Elective(String electiveName, int credits, int capacity) {
        this.electiveName = electiveName;
        this.credits = credits;
        this.capacity = capacity;
        enrolledStudents = new ArrayList<>();
        waitlist = new LinkedList<>();
    }

    public String getElectiveName() {
        return electiveName;
    }

    public int getCredits() {
        return credits;
    }

    public boolean isFull() {
        return enrolledStudents.size() >= capacity;
    }

    public boolean containsStudent(Student student) {
        return enrolledStudents.contains(student)
                || waitlist.contains(student);
    }

    public boolean enroll(Student student) {
        if (isFull()) {
            return false;
        }

        enrolledStudents.add(student);
        student.addCredits(credits);
        return true;
    }

    public boolean drop(Student student) {
        if (!enrolledStudents.remove(student)) {
            return false;
        }

        student.removeCredits(credits);
        return true;
    }

    public void addToWaitlist(Student student) {
        waitlist.add(student);
    }

    public int getWaitlistPosition() {
        return waitlist.size();
    }

    public void promoteNextStudent() {
        while (!waitlist.isEmpty() && !isFull()) {
            Student student = waitlist.poll();

            if (student.canEnroll(credits)) {
                enrolledStudents.add(student);
                student.addCredits(credits);

                System.out.println(student.getName()
                        + " promoted from waitlist and enrolled in "
                        + electiveName + " (credits: "
                        + student.getCurrentCredits() + "/"
                        + student.getCreditLimit() + ").");
            } else {
                System.out.println("Promotion skipped: "
                        + student.getName()
                        + " would exceed the credit limit.");
            }
        }
    }
}

class EnrollmentService {

    public void enroll(Student student, Elective elective) {
        if (elective.containsStudent(student)) {
            System.out.println("Enrollment failed: "
                    + student.getName()
                    + " is already enrolled or waitlisted.");
            return;
        }

        if (!student.canEnroll(elective.getCredits())) {
            System.out.println("Enrollment failed: "
                    + student.getName() + " would exceed the "
                    + student.getStudentType() + " credit limit ("
                    + (student.getCurrentCredits() + elective.getCredits())
                    + "/" + student.getCreditLimit() + ").");
            return;
        }

        if (elective.isFull()) {
            elective.addToWaitlist(student);

            System.out.println(elective.getElectiveName()
                    + " is full.");

            System.out.println(student.getName()
                    + " added to waitlist (position "
                    + elective.getWaitlistPosition() + ").");
            return;
        }

        elective.enroll(student);

        System.out.println(student.getName() + " enrolled in "
                + elective.getElectiveName() + " (credits: "
                + student.getCurrentCredits() + "/"
                + student.getCreditLimit() + ").");
    }

    public void drop(Student student, Elective elective) {
        if (elective.drop(student)) {
            System.out.println(student.getName() + " dropped "
                    + elective.getElectiveName() + " (credits: "
                    + student.getCurrentCredits() + "/"
                    + student.getCreditLimit() + ").");

            elective.promoteNextStudent();
        } else {
            System.out.println("Drop failed: "
                    + student.getName()
                    + " is not enrolled in "
                    + elective.getElectiveName() + ".");
        }
    }
}

public class Pb4 {
    public static void main(String[] args) {

        EnrollmentService service = new EnrollmentService();

        Elective cloud = new Elective("Cloud Computing", 4, 2);

        Student asha = new Student("Asha", 20, new RegularPolicy());
        Student ravi = new Student("Ravi", 22, new HonorsPolicy());
        Student neha = new Student("Neha", 12, new ExchangePolicy());
        Student kiran = new Student("Kiran", 22, new RegularPolicy());

        service.enroll(asha, cloud);
        service.enroll(ravi, cloud);
        service.enroll(neha, cloud);
        service.enroll(kiran, cloud);

        System.out.println();

        service.drop(asha, cloud);
    }
}
