
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

interface LeavePolicy {
    boolean isLeaveAllowed(long days);
}

abstract class Employee {
    private String name;
    private LeavePolicy leavePolicy;

    public Employee(String name, LeavePolicy leavePolicy) {
        this.name = name;
        this.leavePolicy = leavePolicy;
    }

    public String getName() {
        return name;
    }

    public boolean isLeaveAllowed(long days) {
        return leavePolicy.isLeaveAllowed(days);
    }
}

class FullTimeEmployee extends Employee {
    public FullTimeEmployee(String name) {
        super(name, new FullTimeLeavePolicy());
    }
}

class PartTimeEmployee extends Employee {
    public PartTimeEmployee(String name) {
        super(name, new PartTimeLeavePolicy());
    }
}

class ContractEmployee extends Employee {
    public ContractEmployee(String name) {
        super(name, new ContractLeavePolicy());
    }
}

class FullTimeLeavePolicy implements LeavePolicy {
    @Override
    public boolean isLeaveAllowed(long days) {
        return days <= 30;
    }
}

class PartTimeLeavePolicy implements LeavePolicy {
    @Override
    public boolean isLeaveAllowed(long days) {
        return days <= 15;
    }
}

class ContractLeavePolicy implements LeavePolicy {
    @Override
    public boolean isLeaveAllowed(long days) {
        return days <= 7;
    }
}

enum LeaveStatus {
    PENDING, APPROVED, REJECTED
}

class LeaveRequest {
    private Employee employee;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveStatus status;

    public LeaveRequest(Employee employee, LocalDate startDate,
                        LocalDate endDate) {
        if (startDate == null || endDate == null
                || !startDate.isBefore(endDate)
                || endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Invalid leave dates");
        }

        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = LeaveStatus.PENDING;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void updateStatus(LeaveStatus newStatus) {
        if (status != LeaveStatus.PENDING) {
            System.out.println("Cannot change status: "
                    + status + " request cannot revert to Pending.");
            return;
        }

        if (newStatus == LeaveStatus.PENDING) {
            System.out.println("Request must be reviewed before approval or rejection.");
            return;
        }

        status = newStatus;

        System.out.println("Leave request for " + employee.getName()
                + " " + status.toString().toLowerCase() + ".");
        System.out.println("Status: " + status);
    }
}

class LeaveManager {
    private List<LeaveRequest> requests;

    public LeaveManager() {
        requests = new ArrayList<>();
    }

    public void submitRequest(Employee employee,
                              LocalDate startDate,
                              LocalDate endDate) {

        long days = java.time.temporal.ChronoUnit.DAYS.between(
                startDate, endDate) + 1;

        if (!employee.isLeaveAllowed(days)) {
            System.out.println("Leave request exceeds employee leave policy.");
            return;
        }

        LeaveRequest request = new LeaveRequest(
                employee, startDate, endDate);

        requests.add(request);

        System.out.println("Leave request submitted by "
                + employee.getName() + " for "
                + startDate + " to " + endDate + ". Status: "
                + request.getStatus());
    }

    public void reviewRequest(LeaveRequest request, LeaveStatus decision) {
        if (decision != LeaveStatus.APPROVED
                && decision != LeaveStatus.REJECTED) {
            System.out.println("Invalid review decision.");
            return;
        }

        request.updateStatus(decision);
    }
}

public class Pb4 {
    public static void main(String[] args) {

        LeaveManager manager = new LeaveManager();

        Employee john = new FullTimeEmployee("John Doe");

        Employee jane = new PartTimeEmployee("Jane Smith");

        LeaveRequest johnRequest = new LeaveRequest(
                john,
                LocalDate.of(2024, 10, 10),
                LocalDate.of(2024, 10, 12));

        System.out.println("Leave request submitted by "
                + john.getName() + " for 2024-10-10 to 2024-10-12. "
                + "Status: " + johnRequest.getStatus());

        manager.reviewRequest(johnRequest, LeaveStatus.APPROVED);

        LeaveRequest janeRequest = new LeaveRequest(
                jane,
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 11, 5));

        System.out.println("Leave request submitted by "
                + jane.getName() + " for 2024-11-01 to 2024-11-05. "
                + "Status: " + janeRequest.getStatus());

        manager.reviewRequest(johnRequest, LeaveStatus.PENDING);
    }
}
