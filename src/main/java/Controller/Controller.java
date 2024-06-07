package Controller;

import Model.Pet;
import Model.PetType;
import Service.DataPetDatabaseService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Controller {
    private PetDatabaseService<Pet> petDatabaseService;


    public Controller(PetDatabaseService<Pet> petDatabaseService) {
        this.petDatabaseService = petDatabaseService;
        this.creator = new PetCreator();
        this.view = new ConsoleView();
        this.validator = new Validator();
    }

    public void createPet(PetType type) {
        byte bytes[] = view.getName().getBytes(StandardCharsets.UTF_8);
        String right_name = new String(bytes, StandardCharsets.UTF_8);
        String[] data = new String[] { right_name, view.getBirthday()};
        validator.validate(data);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthday = LocalDate.parse(data[1], formatter);
        try {
            int res = petDatabaseService.create(creator.createPet(type, data[0], birthday));
            view.showMessage(String.format("%d Запись добавлена", res));
        } catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }

    }

    public void updatePet(int id) {

        Pet pet = petDatabaseService.getById(id);
        String[] data = new String[] { view.getName(), view.getBirthday() };

        validator.validate(data);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthday = LocalDate.parse(data[1], formatter);
        pet.setName(data[0]);
        pet.setBirthday(birthday);
        try {
            int res = petDatabaseService.update(pet);
            view.showMessage(String.format("%d запись изменена \n", res));
        } catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }

    }

    public void getAllPet() {
        try {
            view.printAll(petDatabaseService.getAll(), Pet.class);
        } catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }
    }

    public boolean trainPet(int id, String command) {
        try {
            if (((DataPetDatabaseService) petDatabaseService).getCommandsById(id, 1).contains(command))
                view.showMessage("это мы уже умеем");
            else {
                if (!((DataPetDatabaseService) petDatabaseService).getCommandsById(id, 2).contains(command))
                    view.showMessage("невыполнимая команда");
                else {
                    ((DataPetDatabaseService) petDatabaseService).train(id, command);
                    view.showMessage("команда разучена\n");
                    return true;
                }
            }
        } catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }
        return false;
    }

    public Pet getById(int id) {
        try {
            return petDatabaseService.getById(id);
        } catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }
        return null;
    }

    public void delete(int id) {
        try {
            petDatabaseService.delete(id);
            view.showMessage("запись удалена");
        } catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }
    }

    public void getCommands(int id) {
        try {
            view.printAll(((DataPetDatabaseService) petDatabaseService).getCommandsById(id, 1), String.class);
        } catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }
    }
}
