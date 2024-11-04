import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

class Expense {
    private double amount;
    private String date;
    private String category;
    private String description;

    public Expense(double amount, String date, String category, String description) {
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.description = description;
    }

    public double getAmount() { return amount; }
    public String getDate() { return date; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return "Amount: " + amount + ", Date: " + date + ", Category: " + category + ", Description: " + description;
    }
}

public class PersonalExpenseTracker {
    private List<Expense> expenses;
    private static final String FILE_PATH = "expenses.txt";

    public PersonalExpenseTracker() {
        expenses = new ArrayList<>();
        loadFromFile();
    }

    public void addExpense(double amount, String date, String category, String description) {
        Expense expense = new Expense(amount, date, category, description);
        expenses.add(expense);
        System.out.println("Expense added successfully.");
    }

    public void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            for (int i = 0; i < expenses.size(); i++) {
                System.out.println((i + 1) + ". " + expenses.get(i));
            }
        }
    }

    public void deleteExpense(int index) {
        if (index < 1 || index > expenses.size()) {
            System.out.println("Invalid index.");
        } else {
            expenses.remove(index - 1);
            System.out.println("Expense deleted successfully.");
        }
    }

    public void displaySummary() {
        HashMap<String, Double> categoryTotals = new HashMap<>();
        double total = 0;

        for (Expense expense : expenses) {
            categoryTotals.put(expense.getCategory(),
                categoryTotals.getOrDefault(expense.getCategory(), 0.0) + expense.getAmount());
            total += expense.getAmount();
        }

        System.out.println("Expense Summary by Category:");
        for (String category : categoryTotals.keySet()) {
            System.out.println(category + ": $" + categoryTotals.get(category));
        }
        System.out.println("Total Spending: $" + total);
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Expense expense : expenses) {
                writer.write(expense.getAmount() + "," + expense.getDate() + "," +
                    expense.getCategory() + "," + expense.getDescription());
                writer.newLine();
            }
            System.out.println("Expenses saved successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving expenses.");
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        double amount = Double.parseDouble(parts[0]);
                        String date = parts[1];
                        String category = parts[2];
                        String description = parts[3];
                        expenses.add(new Expense(amount, date, category, description));
                    }
                }
                System.out.println("Expenses loaded successfully.");
            } catch (IOException | NumberFormatException e) {
                System.out.println("An error occurred while loading expenses.");
            }
        }
    }

    public static void main(String[] args) {
        PersonalExpenseTracker tracker = new PersonalExpenseTracker();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Personal Expense Tracker ---");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Delete Expense");
            System.out.println("4. Display Summary");
            System.out.println("5. Save and Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    System.out.print("Enter category (e.g., Food, Transport): ");
                    String category = scanner.nextLine();
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();
                    tracker.addExpense(amount, date, category, description);
                    break;
                case 2:
                    tracker.viewExpenses();
                    break;
                case 3:
                    System.out.print("Enter the expense number to delete: ");
                    int index = scanner.nextInt();
                    tracker.deleteExpense(index);
                    break;
                case 4:
                    tracker.displaySummary();
                    break;
                case 5:
                    tracker.saveToFile();
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}