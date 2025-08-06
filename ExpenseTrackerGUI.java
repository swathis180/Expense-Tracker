import java.awt.*;
import java.util.List;
import javax.swing.*;

public class ExpenseTrackerGUI extends JFrame {
    private JTextField categoryField, amountField, dateField, searchField;
    private JTextArea outputArea;
    private ExpenseManager manager = new ExpenseManager(); // LINK TO CONTROLLER

    public ExpenseTrackerGUI() {
        setTitle("Expense Tracker");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Input Panel ---
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Expense"));

        inputPanel.add(new JLabel("Category:"));
        categoryField = new JTextField();
        inputPanel.add(categoryField);

        inputPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        inputPanel.add(amountField);

        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        inputPanel.add(dateField);

        JButton addBtn = new JButton("Add Expense");
        inputPanel.add(addBtn);

        JButton clearBtn = new JButton("Clear");
        inputPanel.add(clearBtn);

        add(inputPanel, BorderLayout.NORTH);

        // --- Output Area ---
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // --- Bottom Panel ---
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton viewBtn = new JButton("View All");
        JButton totalBtn = new JButton("Total Expenses");
        JButton searchBtn = new JButton("Search");
        searchField = new JTextField(10);

        bottomPanel.add(viewBtn);
        bottomPanel.add(new JLabel("Search:"));
        bottomPanel.add(searchField);
        bottomPanel.add(searchBtn);
        bottomPanel.add(totalBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Actions (Connecting GUI to Logic) ---
        addBtn.addActionListener(e -> {
            try {
                String category = categoryField.getText();
                double amount = Double.parseDouble(amountField.getText());
                String date = dateField.getText();

                manager.addExpense(new Expense(category, amount, date));
                outputArea.setText("✅ Expense Added!\n");
            } catch (Exception ex) {
                outputArea.setText("❌ Error adding expense.");
            }
        });

        viewBtn.addActionListener(e -> {
            outputArea.setText("");
            List<Expense> expenses = manager.loadExpenses();
            if (expenses.isEmpty()) {
                outputArea.setText("No expenses found!");
            } else {
                for (Expense exp : expenses) {
                    outputArea.append("Category: " + exp.category +
                                      " | Amount: " + exp.amount +
                                      " | Date: " + exp.date + "\n");
                }
            }
        });

        searchBtn.addActionListener(e -> {
            outputArea.setText("");
            List<Expense> results = manager.searchByCategory(searchField.getText());
            if (results.isEmpty()) {
                outputArea.setText("No expenses found for: " + searchField.getText());
            } else {
                for (Expense exp : results) {
                    outputArea.append("Category: " + exp.category +
                                      " | Amount: " + exp.amount +
                                      " | Date: " + exp.date + "\n");
                }
            }
        });

        totalBtn.addActionListener(e -> {
            double total = manager.getTotalExpenses();
            outputArea.setText("💰 Total Expenses: " + total);
        });

        clearBtn.addActionListener(e -> {
            categoryField.setText("");
            amountField.setText("");
            dateField.setText("");
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ExpenseTrackerGUI::new);
    }
}