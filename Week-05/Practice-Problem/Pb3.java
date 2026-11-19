
import java.util.Arrays;

class PatientVitals {

    private double[] readings;
    private int count;

    public PatientVitals(double[] initialReadings) {

        readings = new double[500];
        count = 0;

        if (initialReadings != null) {
            for (double reading : initialReadings) {
                recordReading(reading);
            }
        }
    }

    public void recordReading(double reading) {

        if (reading > 0 && reading <= 45 && count < 500) {
            readings[count] = reading;
            count++;
        }
    }

    public double getAverage() {

        if (count == 0) {
            return 0;
        }

        double sum = 0;

        for (int i = 0; i < count; i++) {
            sum += readings[i];
        }

        return sum / count;
    }

    public double[] getAllReadings() {
        return Arrays.copyOf(readings, count);
    }
}

public class Pb3 {

    public static void main(String[] args) {

        PatientVitals v = new PatientVitals(
                new double[]{36.5, -2, 37.1}
        );

        System.out.println("Valid readings: " +
                           Arrays.toString(v.getAllReadings()));

        System.out.println("Average: " + v.getAverage());

        double[] copy = v.getAllReadings();
        copy[0] = 999;

        System.out.println("After modifying copy: " +
                           Arrays.toString(v.getAllReadings()));

        v.recordReading(38.0);
        v.recordReading(50.0);

        System.out.println("Final readings: " +
                           Arrays.toString(v.getAllReadings()));
    }
}
