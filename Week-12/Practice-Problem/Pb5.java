
import java.util.Scanner;

public class Pb5 {

    static class Candidate {
        String name;
        int[] scores;
        int total;
        int bestClimb;
        int startTest;
        int endTest;

        Candidate(String name, int[] scores) {
            this.name = name;
            this.scores = scores;

            for (int score : scores) {
                total += score;
            }

            findBestClimb();
        }

        void findBestClimb() {

            int currentSum = 0;
            int currentStart = 0;

            bestClimb = Integer.MIN_VALUE;

            for (int i = 0; i < scores.length - 1; i++) {

                int change = scores[i + 1] - scores[i];

                currentSum += change;

                if (currentSum > bestClimb) {
                    bestClimb = currentSum;
                    startTest = currentStart + 1;
                    endTest = i + 2;
                }

                if (currentSum < 0) {
                    currentSum = 0;
                    currentStart = i + 1;
                }
            }
        }
    }

    public static void mergeSort(Candidate[] arr, int left, int right) {

        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;

        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);

        merge(arr, left, mid, right);
    }

    public static void merge(Candidate[] arr, int left, int mid, int right) {

        Candidate[] temp = new Candidate[right - left + 1];

        int i = left;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= right) {

            if (arr[i].total >= arr[j].total) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        while (j <= right) {
            temp[k++] = arr[j++];
        }

        for (int x = 0; x < temp.length; x++) {
            arr[left + x] = temp[x];
        }
    }

    public static int countCutoff(Candidate[] arr, int cutoff) {

        int left = 0;
        int right = arr.length;

        while (left < right) {

            int mid = left + (right - left) / 2;

            if (arr[mid].total >= cutoff) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    public static void findPair(Candidate[] arr, int target) {

        int left = 0;
        int right = arr.length - 1;

        while (left < right) {

            int sum = arr[left].total + arr[right].total;

            if (sum == target) {
                System.out.println("PAIR " + target + ": "
                        + arr[left].name + " + " + arr[right].name);
                return;
            }

            if (sum > target) {
                left++;
            } else {
                right--;
            }
        }

        System.out.println("PAIR " + target + ": no pair");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        Candidate[] candidates = new Candidate[n];

        for (int i = 0; i < n; i++) {

            String name = sc.next();

            int[] scores = new int[6];

            for (int j = 0; j < 6; j++) {
                scores[j] = sc.nextInt();
            }

            candidates[i] = new Candidate(name, scores);
        }

        mergeSort(candidates, 0, n - 1);

        System.out.println("Shortlist:");

        for (int i = 0; i < n; i++) {

            Candidate c = candidates[i];

            System.out.println((i + 1) + ". " + c.name + " "
                    + c.total + " | best climb +" + c.bestClimb
                    + " (test " + c.startTest + " to " + c.endTest + ")");
        }

        while (sc.hasNext()) {

            String query = sc.next();

            if (query.equalsIgnoreCase("CUTOFF")) {

                int cutoff = sc.nextInt();

                int count = countCutoff(candidates, cutoff);

                System.out.println("CUTOFF " + cutoff + ": "
                        + count + " candidates");
            }

            else if (query.equalsIgnoreCase("PAIR")) {

                int target = sc.nextInt();

                findPair(candidates, target);
            }
        }
    }
}
