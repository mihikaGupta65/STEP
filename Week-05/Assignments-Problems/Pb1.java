
class LibraryMember {

    private String membershipId;
    String branchCode;
    protected double finesOwed;
    public String displayName;

    public LibraryMember(String membershipId, String branchCode,
                         double finesOwed, String displayName) {

        if (membershipId == null || membershipId.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid Membership ID");
        }

        this.membershipId = membershipId.trim();
        this.branchCode = branchCode;
        this.finesOwed = finesOwed;
        this.displayName = displayName;
    }

    static String classifyAccess(String fieldModifier, String accessorContext) {

        switch (fieldModifier) {

            case "private":
                return accessorContext.equals("SAME_CLASS") ? "ALLOWED" : "DENIED";

            case "default":
                return accessorContext.equals("SAME_CLASS") ||
                       accessorContext.equals("SAME_PACKAGE") ? "ALLOWED" : "DENIED";

            case "protected":
                return accessorContext.equals("SAME_CLASS") ||
                       accessorContext.equals("SAME_PACKAGE") ? "ALLOWED" : "DENIED";

            case "public":
                return "ALLOWED";

            default:
                return "DENIED";
        }
    }

    static String summarizeByModifier(String[][] attempts) {

        String[] modifiers = {"private", "default", "protected", "public"};

        StringBuilder result = new StringBuilder();

        for (String modifier : modifiers) {

            int allowed = 0;
            int denied = 0;

            for (String[] attempt : attempts) {

                if (attempt != null && attempt.length >= 2 &&
                    modifier.equals(attempt[0])) {

                    if (classifyAccess(attempt[0], attempt[1]).equals("ALLOWED")) {
                        allowed++;
                    } else {
                        denied++;
                    }
                }
            }

            if (result.length() > 0) {
                result.append(" | ");
            }

            result.append(modifier)
                  .append(": ")
                  .append(allowed)
                  .append(" allowed / ")
                  .append(denied)
                  .append(" denied");
        }

        return result.toString();
    }

    String getMembershipId() {
        return membershipId;
    }
}

public class Pb1 {

    public static void main(String[] args) {

        System.out.println(
            LibraryMember.classifyAccess("private", "SAME_CLASS")
        );

        System.out.println(
            LibraryMember.classifyAccess("protected", "DIFFERENT_PACKAGE")
        );

        String[][] attempts = {
            {"private", "SAME_CLASS"},
            {"private", "SAME_PACKAGE"},
            {"default", "SAME_PACKAGE"},
            {"default", "DIFFERENT_PACKAGE"},
            {"protected", "SAME_PACKAGE"},
            {"protected", "SAME_CLASS"},
            {"public", "DIFFERENT_PACKAGE"}
        };

        System.out.println(
            LibraryMember.summarizeByModifier(attempts)
        );

        try {
            LibraryMember member1 =
                    new LibraryMember("LB9", "BR1", 0, "Priya Nair");

            System.out.println("Member created: " + member1.getMembershipId());

        } catch (IllegalArgumentException e) {
            System.out.println("Construction rejected");
        }

        try {
            LibraryMember member2 =
                    new LibraryMember("LB94", "BR1", 0, "Priya Nair");

            System.out.println("Member created: " + member2.getMembershipId());

        } catch (IllegalArgumentException e) {
            System.out.println("Construction rejected");
        }
    }
}
