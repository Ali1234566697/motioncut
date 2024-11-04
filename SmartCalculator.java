import java.util.Scanner;

public class SmartCalculator {
    private Scanner scanner;

    public SmartCalculator() {
        scanner = new Scanner(System.in);
    }

    // Arithmetic operations
    public double add(double a, double b) { return a + b; }
    public double subtract(double a, double b) { return a - b; }
    public double multiply(double a, double b) { return a * b; }
    public double divide(double a, double b) throws ArithmeticException {
        if (b == 0) throw new ArithmeticException("Cannot divide by zero.");
        return a / b;
    }

    public void start() {
        while (true) {
            System.out.println("\n--- Smart Calculator ---");
            System.out.println("1. Add");
            System.out.println("2. Subtract");
            System.out.println("3. Multiply");
            System.out.println("4. Divide");
            System.out.println("5. Exit");
            System.out.print("Choose an operation: ");

            int choice = scanner.nextInt();
            if (choice == 5) {
                System.out.println("Goodbye!");
                break;
            }

            // Get input for the numbers
            System.out.print("Enter the first number: ");
            double num1 = scanner.nextDouble();
            System.out.print("Enter the second number: ");
            double num2 = scanner.nextDouble();

            try {
                double result = 0;
                switch (choice) {
                    case 1:
                        result = add(num1, num2);
                        System.out.println("Result: " + result);
                        break;
                    case 2:
                        result = subtract(num1, num2);
                        System.out.println("Result: " + result);
                        break;
                    case 3:
                        result = multiply(num1, num2);
                        System.out.println("Result: " + result);
                        break;
                    case 4:
                        result = divide(num1, num2);
                        System.out.println("Result: " + result);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (ArithmeticException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        SmartCalculator calculator = new SmartCalculator();
        calculator.start();
    }
}