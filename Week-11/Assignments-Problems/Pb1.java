
import java.util.Arrays;
import java.util.Stack;

public class Pb1 {

    public static int[] priceStreak(int[] price) {

        int n = price.length;
        int[] result = new int[n];

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {

            while (!stack.isEmpty() && price[stack.peek()] <= price[i]) {
                stack.pop();
            }

            if (stack.isEmpty()) {
                result[i] = i + 1;
            } else {
                result[i] = i - stack.peek();
            }

            stack.push(i);
        }

        return result;
    }

    public static void main(String[] args) {

        int[] price = {100, 80, 60, 70, 60, 75, 85};

        int[] result = priceStreak(price);

        System.out.println("Price: " + Arrays.toString(price));
        System.out.println("Price Streak: " + Arrays.toString(result));

        int[] price2 = {10, 20, 30};

        int[] result2 = priceStreak(price2);

        System.out.println("Price: " + Arrays.toString(price2));
        System.out.println("Price Streak: " + Arrays.toString(result2));
    }
}
