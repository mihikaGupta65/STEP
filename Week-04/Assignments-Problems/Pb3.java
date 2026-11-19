

class Canteen {

    private String canteenCode;
    private String canteenName;
    private int trustScore;

    public Canteen(String canteenCode, String canteenName, int trustScore) {
        this.canteenCode = canteenCode;
        this.canteenName = canteenName;
        this.trustScore = trustScore;
    }

    public Canteen(String canteenCode, String canteenName) {
        this(canteenCode, canteenName, 3);
    }

    int compareTo(Canteen other) {

        if (this.trustScore != other.trustScore) {
            return Integer.compare(other.trustScore, this.trustScore);
        }

        int codeResult = this.canteenCode.compareToIgnoreCase(other.canteenCode);

        if (codeResult != 0) {
            return codeResult;
        }

        return Integer.compare(this.canteenName.length(), other.canteenName.length());
    }

    static Canteen[] rankCanteens(Canteen[] canteens) {

        if (canteens == null) {
            return new Canteen[0];
        }

        Canteen[] ranked = canteens.clone();

        for (int i = 0; i < ranked.length - 1; i++) {

            for (int j = 0; j < ranked.length - 1 - i; j++) {

                if (ranked[j].compareTo(ranked[j + 1]) > 0) {

                    Canteen temp = ranked[j];
                    ranked[j] = ranked[j + 1];
                    ranked[j + 1] = temp;
                }
            }
        }

        return ranked;
    }

    String getCanteenCode() {
        return canteenCode;
    }
}

public class Pb3 {

    public static void main(String[] args) {

        Canteen[] canteens = {
            new Canteen("HB3-C", "Spice Junction", 3),
            new Canteen("hb1-c", "Grand Mess", 5),
            new Canteen("HB2-C", "Southern Treats")
        };

        Canteen[] ranked = Canteen.rankCanteens(canteens);

        System.out.println("Ranked Canteens:");

        for (Canteen canteen : ranked) {
            System.out.println(canteen.getCanteenCode());
        }
    }
}
