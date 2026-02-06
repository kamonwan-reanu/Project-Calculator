import java.util.Scanner;
import java.util.InputMismatchException;

class Calculator {
    private final double currentResult;
    private double num1, num2;
    private String operator;

    public Calculator(double value, double num1, double num2, String op) {
        this.currentResult = value;
        this.num1 = num1;
        this.num2 = num2;
        this.operator = op;
    }

    public Calculator execute(String op, double nextNum) throws ArithmeticException, IllegalArgumentException {
        double newResult = currentResult;
        switch (op) {
            case "+": newResult += nextNum; break;
            case "-": newResult -= nextNum; break;
            case "*": newResult *= nextNum; break;
            case "/": 
                if (nextNum == 0) {
                    throw new ArithmeticException("Division by zero!");
                }
                newResult /= nextNum;
                break;
            case "%": 
                if (nextNum == 0) {
                    throw new ArithmeticException("Modulo by zero!");
                }
                newResult %= nextNum;
                break;
            case "^": 
                newResult = Math.pow(currentResult, nextNum); 
                break;
            default:
                throw new IllegalArgumentException("Invalid Operator: " + op);
        }
        return new Calculator(newResult, currentResult, nextNum, op);
    }

    public void display() {
        System.out.printf("\n %.2f %s %.2f = %.2f \n", num1, operator, num2, currentResult);
    }

    public double getResult() {
        return currentResult;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);

        System.out.println("--- Calculator Program ---");
        
        double start = 0;
        try {
            System.out.print("\nEnter number: ");
            start = kb.next();
        } catch (InputMismatchException e) {
            System.out.println(">>> Critical Error: Invalid input type.");
            return;
        }

        Calculator calc = new Calculator(start, 0, 0, "");

        while (true) {
            try {
                System.out.print("\n(+, -, *, /, %) | 'c' Clear | 'q' Quit: ");
                String op = kb.next();

                if (op.equals("q")) break;

                if (op.equals("c")) {
                    System.out.println("Cleared.");
                    System.out.print("\nEnter number: ");
                    calc = new Calculator(kb.nextDouble(), 0, 0, "");
                    continue;
                }

                System.out.print("\nNext number: ");
                double nextNum = kb.nextDouble();

                calc = calc.execute(op, nextNum);
                calc.display();

            } catch (InputMismatchException e) {
                // ดักจับพิมพ์ตัวเลขผิด
                System.out.println(">>> Error: Please enter a valid number!");
                kb.nextLine();
            } catch (ArithmeticException | IllegalArgumentException e) {
                // ดักจับ Error ที่มาจากคลาส Calculator
                System.out.println(">>> Error: " + e.getMessage());
            }
        }

        System.out.println("\nFinal result: " + calc.getResult() +"\n");
        kb.close();
    }
}
