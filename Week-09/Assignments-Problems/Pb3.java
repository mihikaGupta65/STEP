
import java.util.*;

public class Pb3 {

    public static long countPeriods(int[] transactions, int k) {
        HashMap<Long, Long> map = new HashMap<>();

        long prefixSum = 0;
        long count = 0;

        map.put(0L, 1L);

        for (int value : transactions) {
            prefixSum += value;

            long required = prefixSum - k;

            count += map.getOrDefault(required, 0L);

            map.put(prefixSum,
                    map.getOrDefault(prefixSum, 0L) + 1);
        }

        return count;
    }

    public static void main(String[] args) {
        int[] transactions = {3, 4, -7, 1, 3, 3, 1, -4};
        int k = 7;

        System.out.println(countPeriods(transactions, k));

        int[] transactions2 = {1, 2, 3};
        int k2 = 10;

        System.out.println(countPeriods(transactions2, k2));
    }
}
