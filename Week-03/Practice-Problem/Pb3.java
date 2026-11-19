
class HostelRoom {

    String roomNo;
    int beds;
    int occupied;

    HostelRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    void allot(String name) {
        if (occupied < beds) {
            occupied++;
            System.out.println(name + " allotted to room " + roomNo);
        }
    }
}

public class Pb3 {

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

    static void safeAllot(HostelRoom[] rooms, String studentName) {

        HostelRoom availableRoom = findAvailableRoom(rooms);

        if (availableRoom != null) {
            availableRoom.allot(studentName);
        } else {
            System.out.println("No rooms available for " + studentName);
        }
    }

    public static void main(String[] args) {

        // First case: One room has an available bed
        HostelRoom[] rooms1 = {
            new HostelRoom("C-214", 3, 2),
            new HostelRoom("C-507", 2, 2)
        };

        System.out.println("First case:");
        safeAllot(rooms1, "Divya");

        // Second case: Every room is full
        HostelRoom[] rooms2 = {
            new HostelRoom("C-214", 3, 3),
            new HostelRoom("C-507", 2, 2)
        };

        System.out.println("\nSecond case:");
        safeAllot(rooms2, "Divya");
    }
}
