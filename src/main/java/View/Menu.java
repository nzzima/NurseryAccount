package View;

import Controller.*;
import Exceptions.UncorrectDataException;
import Model.PetType;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Menu {
    Controller controller;

    public Menu(Controller controller) {
        this.controller = controller;
    }

    public void start() {
        Counter count = new Counter();
        System.out.println("==================РЕЕСТР ДОМАШНИХ ЖИВОТНЫХ==================");
        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            boolean flag = true;
            int id;
            while (flag) {
                System.out.println("\n1) Список всех животных" +
                                   "\n2) Завести новое животное" +
                                   "\n3) Изменить данные о животном" +
                                   "\n4) Команды животного" +
                                   "\n5) Дрессировка" +
                                   "\n6) Удалить запись" +
                                   "\n7) Выход");
                System.out.print("\nВведите число, соответствующее команде --> ");
                String key = scanner.next();
                switch (key) {
                    case "1":
                        controller.getAllPet();
                        break;
                    case "2":
                        PetType type = petChoice(scanner);
                        if (type != null) {
                            try {
                                controller.createPet(type);
                                count.add();
                                System.out.println("SUCCESS CREATE");
                            } catch (UncorrectDataException error) {
                                System.out.println(error.getMessage());
                            }
                        }
                        break;
                    case "3":
                        while (true) {
                            id = idChoicePet(scanner);
                            if (id != 0)
                                try {
                                    controller.updatePet(id);
                                } catch (UncorrectDataException e) {
                                    System.out.println(e.getMessage());
                                }
                            else
                                break;
                        }
                    case "4":
                        while (true) {
                            id = idChoicePet(scanner);
                            if (id != 0)
                                controller.getCommands(id);
                            else
                                break;
                        }
                    case "6":
                        id = idChoicePet(scanner);
                        if (id != 0)
                            controller.delete(id);
                        break;
                }
            }
        }
    }

    private PetType petChoice(Scanner scanner) {
        System.out.println("Какое животное добавить:\n1 - Кошка\n2 - Собака\n3 - Хомяк\n0 - Возврат в основное меню");

        while (true) {
            String key = scanner.next();
            switch (key) {
                case "1":
                    return PetType.Cat;
                case "2":
                    return PetType.Dog;
                case "3":
                    return PetType.Hamster;
                case "0":
                    return null;
                default:
                    System.out.println("Такого животного нету, введите число от 0 до 3");
                    break;
            }
        }
    }
    private Integer idChoicePet(Scanner scanner) {
        System.out.print("\nВведите номер животного, 0 для возврата в основное меню: ");
        while(true) {
            int id = scanner.nextInt();
            scanner.nextLine();
            if (id == 0)
                return id;
            if (controller.getById(id) == null) {
                System.out.println("\nЖивотного с таким номером нет, попробуйте еще раз, 0 для возврата в основное меню");
            } else
                return id;
        }
    }
}
