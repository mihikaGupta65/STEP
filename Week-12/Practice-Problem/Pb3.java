
import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;

public class Pb3 {

    static class Job {
        String name;
        int remainingTime;

        Job(String name, int time) {
            this.name = name;
            this.remainingTime = time;
        }
    }

    static class LabScheduler {

        Queue<Job> queue = new LinkedList<>();
        int quantum;
        int currentTime = 0;

        LabScheduler(int quantum) {
            this.quantum = quantum;
        }

        void addJob(String name, int time) {
            queue.add(new Job(name, time));
        }

        void schedule() {

            StringBuilder slices = new StringBuilder();
            StringBuilder finishTimes = new StringBuilder();

            while (!queue.isEmpty()) {

                Job job = queue.poll();

                int start = currentTime;
                int runTime = Math.min(quantum, job.remainingTime);

                currentTime += runTime;
                job.remainingTime -= runTime;

                if (slices.length() > 0) {
                    slices.append(" ");
                }

                slices.append(job.name)
                      .append("(")
                      .append(start)
                      .append("-")
                      .append(currentTime)
                      .append(")");

                if (job.remainingTime > 0) {
                    queue.add(job);
                } else {
                    finishTimes.append("finish ")
                                .append(job.name)
                                .append(" ")
                                .append(currentTime)
                                .append("\n");
                }
            }

            System.out.println(slices);
            System.out.print(finishTimes);
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter quantum: ");
        int quantum = sc.nextInt();

        System.out.print("Enter number of jobs: ");
        int n = sc.nextInt();

        LabScheduler scheduler = new LabScheduler(quantum);

        for (int i = 0; i < n; i++) {
            String name = sc.next();
            int time = sc.nextInt();

            scheduler.addJob(name, time);
        }

        scheduler.schedule();
    }
}
