package View;

import Model.Pet;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class DataViewService implements ViewService{

    Scanner scanner;
    public DataViewService() {
        scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    }

    @Override
    public String getName() {
        System.out.print("Имя --> ");
        return scanner.nextLine();
    }

    @Override
    public String getBirthday() {
        System.out.print("Введите дату рождения в формате`DD.MM.YYYY` --> ");
        return scanner.nextLine();
    }

    @Override
    public void printAll(List<Pet> list) {
        if (list.isEmpty())
            System.out.println("Список питомцев пуст!");
        else {
            System.out.println("Питомцы: ");
            for (Pet item : list) {
                System.out.println(item);
            }
        }
    }

    @Override
    public void printComs(List<String> list) {
        if (list.isEmpty())
            System.out.println("Список команд пуст!");
        else {
            System.out.println("Команды: ");
            for (String item : list) {
                System.out.println(item);
            }
        }
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
}
