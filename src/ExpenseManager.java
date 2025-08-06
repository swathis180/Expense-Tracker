import java.io.*;
import java.util.*;

public class ExpenseManager {
    private static final String FILE_NAME = "expenses.txt";

    // Add Expense
    public void addExpense(Expense e) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(e.toString());
            bw.newLine();
        }
    }

    // Load all expenses
    public List<Expense> loadExpenses() {
        List<Expense> expenses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                expenses.add(Expense.fromString(line));
            }
        } catch (IOException e) {
            // Ignore if file doesn't exist
        }
        return expenses;
    }

    // Search by category
    public List<Expense> searchByCategory(String keyword) {
        List<Expense> result = new ArrayList<>();
        for (Expense e : loadExpenses()) {
            if (e.category.toLowerCase().contains(keyword.toLowerCase())) {
                result.add(e);
            }
        }
        return result;
    }

    // Calculate total expenses
    public double getTotalExpenses() {
        double total = 0;
        for (Expense e : loadExpenses()) {
            total += e.amount;
        }
        return total;
    }
}
