package Controller;

import Model.Creator;
import Model.Pet;
import Model.PetCreator;
import Model.PetType;
import Service.DataPetDatabaseService;
import Service.PetDatabaseService;
import View.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Controller {
    private PetDatabaseService<Pet> petDatabaseService;
    private Creator creator;
    private View<Pet> view;

    public Controller(PetDatabaseService<Pet> petDatabaseService) {
        this.petDatabaseService = petDatabaseService;
        this.creator = new PetCreator();
        this.view = new ConsoleView();
    }

    public void createPet(PetType type) {

        String[] data = new String[] { view.getName(), view.getBirthday() };
        validator.validate(data);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthday = LocalDate.parse(data[1], formatter);
        try {
            int res = petDatabaseService.create(PetCreator.createPet(type, data[0], birthday));
            view.showMessage(String.format("%d запись добавлена", res));
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