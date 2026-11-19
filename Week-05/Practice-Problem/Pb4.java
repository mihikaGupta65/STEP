
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

class PatientProfile {

    private String patientId;
    private String name;
    private boolean discharged;
    private String lockerPinHash;

    public PatientProfile() {
        this(null, null);
    }

    public PatientProfile(String name) {
        this(null, name);
    }

    public PatientProfile(String patientId, String name) {
        this.name = name;
        setPatientId(patientId);
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String id) {
        if (patientId == null && id != null && !id.trim().isEmpty()) {
            patientId = id;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDischarged() {
        return discharged;
    }

    public void setDischarged(boolean discharged) {
        this.discharged = discharged;
    }

    public void setLockerPin(String pin) {

        if (pin != null && pin.matches("[0-9]{4,6}")) {

            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");

                byte[] hash = md.digest(
                        pin.getBytes(StandardCharsets.UTF_8)
                );

                StringBuilder result = new StringBuilder();

                for (byte b : hash) {
                    result.append(String.format("%02x", b));
                }

                lockerPinHash = result.toString();

            } catch (Exception e) {
                throw new IllegalStateException("Unable to process PIN");
            }
        }
    }
}

public class Pb4 {

    public static void main(String[] args) {

        PatientProfile p1 = new PatientProfile("Arjun Iyer");

        System.out.println("Patient ID: " + p1.getPatientId());

        PatientProfile p2 = new PatientProfile(
                "MT2026-0142", "Arjun Iyer"
        );

        System.out.println("Patient ID: " + p2.getPatientId());

        PatientProfile p3 = new PatientProfile();

        p3.setPatientId("MT2026-0142");
        p3.setPatientId("HACKED-0000");

        System.out.println("Patient ID: " + p3.getPatientId());

        p3.setName("Arjun Iyer");
        p3.setDischarged(true);
        p3.setLockerPin("1234");

        System.out.println("Patient Name: " + p3.getName());
        System.out.println("Discharged: " + p3.isDischarged());
    }
}
