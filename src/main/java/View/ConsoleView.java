package View;

import Model.Pet;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ConsoleView implements View {
    Scanner scanner;

    public ConsoleView() {
        scanner = new Scanner(System.in, "ibm866");
    }
    @Override
    public String getName() {
        System.out.print("Имя: ");
        return scanner.nextLine();
    }

    @Override
    public String getBirthday() {
        System.out.print("Введите дату рождения в формате 'dd.mm.yyyy': ");
        return scanner.nextLine();
    }

    @Override
    public void printAll (List list, Class cl) {
        System.out.print("\033[H\033[J");
        if (list.isEmpty())
            System.out.println("список пуст");
        else {
            if (cl == Pet.class)
                System.out.println("\n          Наши питомцы:");
            for (Object item : list) {
                System.out.println(item);
            }
        }
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
}
