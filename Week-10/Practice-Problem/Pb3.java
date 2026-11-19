
public class Pb3 {

    public static void main(String[] args) {

        String[] students = {"Anu", "Ravi", "Meena"};
        String[] subjects = {"Math", "Sci", "Eng"};

        int[][] marks = {
            {80, 90, 70},
            {60, 85, -1},
            {95, 75, 88}
        };

        System.out.println("Totals");

        for (int i = 0; i < students.length; i++) {
            int total = 0;

            for (int j = 0; j < subjects.length; j++) {
                if (marks[i][j] != -1) {
                    total += marks[i][j];
                }
            }

            System.out.println(students[i] + " " + total);
        }

        System.out.println();

        System.out.println("Toppers");

        for (int j = 0; j < subjects.length; j++) {
            int max = -1;
            int topperIndex = -1;

            for (int i = 0; i < students.length; i++) {
                if (marks[i][j] > max) {
                    max = marks[i][j];
                    topperIndex = i;
                }
            }

            if (topperIndex != -1) {
                System.out.println(subjects[j] + " "
                        + students[topperIndex] + " " + max);
            }
        }
    }
}
