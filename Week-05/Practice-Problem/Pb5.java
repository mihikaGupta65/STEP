
import java.util.Arrays;

class DischargeSummary {

    private final String patientId;
    private final String[] medicationCodes;

    public DischargeSummary(String patientId, String[] medicationCodes) {

        if (patientId == null || patientId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid patient ID");
        }

        if (medicationCodes == null || medicationCodes.length > 20) {
            throw new IllegalArgumentException("Invalid medication array");
        }

        for (String code : medicationCodes) {
            if (code == null || !code.matches("MED-[A-Z]")) {
                throw new IllegalArgumentException("Invalid medication code");
            }
        }

        this.patientId = patientId;
        this.medicationCodes = Arrays.copyOf(
                medicationCodes, medicationCodes.length
        );
    }

    public String getPatientId() {
        return patientId;
    }

    public String[] getMedicationCodes() {
        return Arrays.copyOf(medicationCodes, medicationCodes.length);
    }

    public DischargeSummary withCorrectedMedication(int index, String newCode) {

        if (index < 0 || index >= medicationCodes.length) {
            throw new IndexOutOfBoundsException("Invalid medication index");
        }

        if (newCode == null || !newCode.matches("MED-[A-Z]")) {
            throw new IllegalArgumentException("Invalid medication code");
        }

        String[] correctedCodes = getMedicationCodes();
        correctedCodes[index] = newCode;

        return new DischargeSummary(patientId, correctedCodes);
    }
}

class CriticalCareDischargeSummary extends DischargeSummary {

    private final int icuDays;

    public CriticalCareDischargeSummary(String patientId,
                                        String[] medicationCodes,
                                        int icuDays) {

        super(patientId, medicationCodes);

        if (icuDays < 0) {
            throw new IllegalArgumentException("Invalid ICU days");
        }

        this.icuDays = icuDays;
    }

    public int getIcuDays() {
        return icuDays;
    }
}

public class Pb5 {

    private static int nightlyProcessed;

    static {
        nightlyProcessed = 0;
    }

    static String processNightlyBatch(DischargeSummary[] summaries) {

        int processed = 0;
        int nullSkipped = 0;
        int criticalCare = 0;
        int routine = 0;

        if (summaries == null) {
            return "0 processed | 0 null skipped | 0 critical-care | 0 routine";
        }

        for (DischargeSummary summary : summaries) {

            if (summary == null) {
                nullSkipped++;
                continue;
            }

            if (summary instanceof CriticalCareDischargeSummary) {
                criticalCare++;
            } else {
                routine++;
            }

            processed++;
        }

        nightlyProcessed += processed;

        return processed + " processed | " +
               nullSkipped + " null skipped | " +
               criticalCare + " critical-care | " +
               routine + " routine";
    }

    public static void main(String[] args) {

        try {
            DischargeSummary invalid = new DischargeSummary(
                    "MT2026-0142",
                    new String[]{"MED-A", "bad"}
            );

            System.out.println("Summary created");

        } catch (IllegalArgumentException e) {
            System.out.println("Construction rejected");
        }

        DischargeSummary d = new DischargeSummary(
                "MT2026-0142",
                new String[]{"MED-A", "MED-B"}
        );

        String[] codes = d.getMedicationCodes();
        codes[0] = "TAMPERED";

        System.out.println("Original medication: " +
                           d.getMedicationCodes()[0]);

        DischargeSummary corrected =
                d.withCorrectedMedication(0, "MED-C");

        System.out.println("Corrected medication: " +
                           corrected.getMedicationCodes()[0]);

        System.out.println("Original summary: " +
                           d.getMedicationCodes()[0]);

        DischargeSummary[] batch = {
                new CriticalCareDischargeSummary(
                        "MT001",
                        new String[]{"MED-X"},
                        4
                ),
                null,
                new DischargeSummary(
                        "MT002",
                        new String[]{"MED-Y"}
                )
        };

        System.out.println(processNightlyBatch(batch));
    }
}
