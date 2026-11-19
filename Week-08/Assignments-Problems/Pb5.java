
import java.util.ArrayList;
import java.util.List;

interface PricingPlan {
    double calculatePrice(double originalPrice);
}

class DayScholarPlan implements PricingPlan {
    public double calculatePrice(double originalPrice) {
        return originalPrice;
    }
}

class HostellerPlan implements PricingPlan {
    public double calculatePrice(double originalPrice) {
        return originalPrice * 0.90;
    }
}

class StaffPlan implements PricingPlan {
    public double calculatePrice(double originalPrice) {
        return originalPrice * 0.80;
    }
}

class Transaction {
    private String description;
    private double amount;

    public Transaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }
}

class Purchase {
    private String itemName;
    private double chargedAmount;
    private boolean refunded;

    public Purchase(String itemName, double chargedAmount) {
        this.itemName = itemName;
        this.chargedAmount = chargedAmount;
        this.refunded = false;
    }

    public String getItemName() {
        return itemName;
    }

    public double getChargedAmount() {
        return chargedAmount;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void markRefunded() {
        refunded = true;
    }
}

class SmartCard {
    private String cardId;
    private PricingPlan pricingPlan;
    private double balance;
    private boolean blocked;
    private List<Transaction> transactions;
    private List<Purchase> purchases;

    public SmartCard(String cardId, PricingPlan pricingPlan) {
        this.cardId = cardId;
        this.pricingPlan = pricingPlan;
        balance = 0;
        blocked = false;
        transactions = new ArrayList<>();
        purchases = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void topUp(double amount) {
        if (blocked) {
            System.out.println("Top-up rejected: Card is blocked.");
            return;
        }

        if (amount < 100) {
            System.out.println("Top-up rejected: Minimum top-up is ₹100.");
            return;
        }

        if (balance + amount > 5000) {
            System.out.println("Top-up rejected: Maximum balance is ₹5000.");
            return;
        }

        balance += amount;
        transactions.add(new Transaction("Top-up", amount));

        System.out.printf("%s topped up with ₹%.2f. Balance: ₹%.2f%n",
                cardId, amount, balance);
    }

    public void purchase(String itemName, double originalPrice) {
        if (blocked) {
            System.out.println("Purchase failed: Card is blocked.");
            return;
        }

        if (originalPrice <= 0) {
            System.out.println("Purchase failed: Invalid item price.");
            return;
        }

        double chargedAmount = pricingPlan.calculatePrice(originalPrice);

        if (balance < chargedAmount) {
            System.out.printf(
                    "Purchase failed: Insufficient balance (required ₹%.2f, available ₹%.2f).%n",
                    chargedAmount, balance);
            return;
        }

        balance -= chargedAmount;

        transactions.add(new Transaction(itemName, -chargedAmount));

        purchases.add(new Purchase(itemName, chargedAmount));

        System.out.printf("%s purchased for ₹%.2f. Balance: ₹%.2f%n",
                itemName, chargedAmount, balance);
    }

    public void refund(String itemName) {
        for (Purchase purchase : purchases) {
            if (purchase.getItemName().equalsIgnoreCase(itemName)) {

                if (purchase.isRefunded()) {
                    System.out.println("Refund rejected: "
                            + itemName + " has already been refunded.");
                    return;
                }

                purchase.markRefunded();

                double amount = purchase.getChargedAmount();

                balance += amount;

                transactions.add(new Transaction("Refund: " + itemName, amount));

                System.out.printf(
                        "Refund of ₹%.2f for %s processed. Balance: ₹%.2f%n",
                        amount, itemName, balance);
                return;
            }
        }

        System.out.println("Refund rejected: Purchase not found.");
    }

    public void blockCard() {
        blocked = true;
        System.out.println(cardId + " is blocked.");
    }

    public void unblockCard() {
        blocked = false;
        System.out.println(cardId + " is unblocked.");
    }

    public void miniStatement() {
        System.out.print("Mini-statement for " + cardId + ": ");

        double total = 0;

        for (int i = 0; i < transactions.size(); i++) {
            double amount = transactions.get(i).getAmount();

            if (amount >= 0) {
                System.out.printf("+%.2f", amount);
            } else {
                System.out.printf("%.2f", amount);
            }

            total += amount;

            if (i < transactions.size() - 1) {
                System.out.print(", ");
            }
        }

        System.out.printf(" = ₹%.2f%n", total);
    }
}

public class Pb5 {
    public static void main(String[] args) {

        SmartCard card = new SmartCard(
                "C-2045", new HostellerPlan());

        card.topUp(500);

        System.out.println();

        card.purchase("Veg Thali", 120);

        card.purchase("Cold Coffee", 60);

        card.purchase("Food Order", 400);

        System.out.println();

        card.refund("Veg Thali");

        card.refund("Veg Thali");

        System.out.println();

        card.miniStatement();
    }
}
