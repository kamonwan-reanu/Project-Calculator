import java.util.Scanner;
import java.util.InputMismatchException;

class Calculator {
    private final double currentResult;

    public Calculator(double value) {
        this.currentResult = value;
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
            default:
                throw new IllegalArgumentException("Invalid Operator: " + op);
        }
        return new Calculator(newResult);
    }

    public void display() {
        System.out.printf("[ Current Result: %.2f ]\n", currentResult);
    }

    public double getResult() {
        return currentResult;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);

        System.out.println("--- Calculator (+ - * / %) ---");
        
        double start = 0;
        // ใช้ try-catch ครอบการรับค่าครั้งแรก
        try {
            System.out.print("Enter number: ");
            start = kb.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println(">>> Critical Error: Invalid input type. Program terminated.");
            return; // จบโปรแกรมถ้าค่าเริ่มต้นผิด
        }

        Calculator calc = new Calculator(start);

        while (true) {
            try {
                System.out.print("\n(+, -, *, /, %) | 'c' Clear | 'q' Quit: ");
                String op = kb.next();

                if (op.equals("q")) break;

                if (op.equals("c")) {
                    System.out.print("Enter number: ");
                    calc = new Calculator(kb.nextDouble());
                    System.out.println("Memory Resetted.");
                    continue;
                }

                System.out.print("Next number: ");
                double nextNum = kb.nextDouble();

                calc = calc.execute(op, nextNum);
                calc.display();

            } catch (InputMismatchException e) {
                // ดักจับกรณีพิมพ์ตัวเลขผิด
                System.out.println(">>> Error: Please enter a valid number!");
                kb.nextLine();
            } catch (ArithmeticException | IllegalArgumentException e) {
                // ดักจับ Error ที่เรา throw มาจากคลาส Calculator
                System.out.println(">>> Error: " + e.getMessage());
            }
        }

        System.out.println("\nFinal result: " + calc.getResult());
        kb.close();
    }
}
