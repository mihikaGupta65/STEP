
class Employee {
    private int empId;
    private String empName;
    private double salary;

    Employee(int empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }

    double getSalary() {
        return salary;
    }
}

class ManagerEmployee extends Employee {
    private double teamBonus;

    ManagerEmployee(int empId, String empName, double salary, double teamBonus) {
        super(empId, empName, salary);
        this.teamBonus = teamBonus;
    }

    double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}

class InternEmployee extends Employee {
    private double stipendCap;

    InternEmployee(int empId, String empName, double salary, double stipendCap) {
        super(empId, empName, salary);
        this.stipendCap = stipendCap;
    }

    double effectiveSalary() {
        return Math.min(getSalary(), stipendCap);
    }
}

class ParkingSlot {
    String slotNo;
    int capacity;
    int occupiedCount;

    ParkingSlot(String slotNo, int capacity, int occupiedCount) {
        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupiedCount = occupiedCount;
    }

    boolean allot(String vehicleNo) {
        if (occupiedCount < capacity) {
            occupiedCount++;
            return true;
        }
        return false;
    }

    static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        if (slots == null) {
            return null;
        }

        for (ParkingSlot slot : slots) {
            if (slot != null && slot.occupiedCount < slot.capacity) {
                return slot;
            }
        }

        return null;
    }

    static void safeAllot(ParkingSlot[] slots, CompanyEmployeeRecord record) {
        if (record == null) {
            return;
        }

        ParkingSlot availableSlot = findAvailableSlot(slots);

        if (availableSlot != null && availableSlot.allot(record.empId)) {
            record.slot = availableSlot;
        } else {
            System.out.println("No parking available for " + record.name);
        }
    }
}

class CompanyEmployeeRecord {
    String name;
    String empId;
    Employee employee;
    ParkingSlot slot;

    static int totalRecords = 0;

    CompanyEmployeeRecord(String name, String empId, Employee employee) {
        this.name = name;
        this.empId = empId;
        this.employee = employee;
        this.slot = null;

        totalRecords++;
    }

    double getEffectivePay() {
        if (employee instanceof ManagerEmployee manager) {
            return manager.effectiveSalary();
        } else if (employee instanceof InternEmployee intern) {
            return intern.effectiveSalary();
        } else if (employee != null) {
            return employee.getSalary();
        }

        return 0;
    }

    String fullProfile() {
        String slotDetails;

        if (slot != null) {
            slotDetails = slot.slotNo;
        } else {
            slotDetails = "no parking assigned";
        }

        return name + " | Pay: Rs " + getEffectivePay()
                + " | Slot: " + slotDetails;
    }
}

public class Pb5 {
    public static void main(String[] args) {

        Employee e1 = new ManagerEmployee(101, "Divya", 70000, 8000);

       Employee e2 = new Employee(102, "Karan", 40000);

        Employee e3 = new InternEmployee(103, "Meera", 12000, 10000);

        CompanyEmployeeRecord r1 =
                new CompanyEmployeeRecord("Divya", "101", e1);

        CompanyEmployeeRecord r2 =
                new CompanyEmployeeRecord("Karan", "102", e2);

        CompanyEmployeeRecord r3 =
                new CompanyEmployeeRecord("Meera", "103", e3);

        CompanyEmployeeRecord[] records = {r1, r2, r3};

        ParkingSlot[] slots = {
            new ParkingSlot("A1", 1, 0),
            new ParkingSlot("A2", 1, 0)
        };

        ParkingSlot.safeAllot(slots, r1);
        ParkingSlot.safeAllot(slots, r2);

        for (CompanyEmployeeRecord record : records) {
            System.out.println(record.fullProfile());
        }

        System.out.println("Total records: "
                + CompanyEmployeeRecord.totalRecords);
    }
}
