public class Expense {
    String category;
    double amount;
    String date;

    public Expense(String category, double amount, String date) {
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public String toString() {
        return category + "," + amount + "," + date;
    }

    public static Expense fromString(String line) {
        String[] parts = line.split(",");
        return new Expense(parts[0], Double.parseDouble(parts[1]), parts[2]);
    }
}
