
public class Pb4 {

    public static int lowerBound(int[] prices, int target) {
        int left = 0;
        int right = prices.length;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (prices[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    public static int upperBound(int[] prices, int target) {
        int left = 0;
        int right = prices.length;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (prices[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    public static void main(String[] args) {
        int[] prices = {100, 150, 150, 200, 300, 450};

        int low = 150;
        int high = 300;

        int first = lowerBound(prices, low);
        int last = upperBound(prices, high);

        System.out.println("Lower bound index " + first);
        System.out.println("Upper bound index " + last);
        System.out.println("Count " + (last - first));
    }
}
