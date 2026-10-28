
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Имя таблицы: ");
        String name = sc.nextLine().trim();
        Table table = new Table(name);

         System.out.println("Введите столбцы (пустая строка — завершить ввод):");
        while (true) {
            System.out.print("  столбец: ");
            String c = sc.nextLine().trim();
            if (c.isEmpty()) break;
            try {
                table.addColumn(c);
            } catch (IllegalArgumentException e) {
                System.out.println("  ! " + e.getMessage());
            }
        }

        if (table.hasColumns()) {
            System.out.println("Введите строки. Для каждой строки — значения по столбцам.");
            while (true) {
                System.out.print("Добавить строку? (y/n): ");
                String ans = sc.nextLine().trim();
                if (!ans.equalsIgnoreCase("y")) break;

                String[] row = new String[table.getColumnCount()];
                for (int i = 0; i < table.getColumnCount(); i++) {
                    System.out.print("  " + table.getColumn(i) + ": ");
                    row[i] = sc.nextLine();
                }
                table.addRow(row);
            }
        } else {
            System.out.println("Столбцов нет — строки не вводятся.");
        }

    }
}
