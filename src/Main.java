
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Имя таблицы: ");
        String name = sc.nextLine().trim();
        Table table = new Table(name);

    }
}