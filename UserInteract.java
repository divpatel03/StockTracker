import java.util.Scanner;

public class UserInteract {
    Scanner scanner = new Scanner(System.in);

    public int askNumber(int input) {
        System.out.print("Enter the number of stocks you want to view: ");
        input = scanner.nextInt();
        scanner.nextLine();

        return input;
    }

    public int askFrequency(int input) {
        System.out.print("Enter the frequency in seconds between prints: ");
        input = scanner.nextInt();

        return input;
    }
}
