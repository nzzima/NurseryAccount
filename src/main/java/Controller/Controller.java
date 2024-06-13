package Controller;

import Model.CreatePet;
import Model.Pet;
import Model.PetType;
import Service.*;
import View.DataViewService;
import View.ViewService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Controller {
    private PetGetAllService petGetAllService;
    private PetCreateService petCreateService;
    private PetUpdateService petUpdateService;
    private PetDeleteService petDeleteService;
    private PetGetByIdService petGetByIdService;
    private PetGetCommandsById petGetCommandsById;
    private PetTrainCommand petTrainCommand;
    private CreatePet createPet;
    private ViewService viewService;


    public Controller(PetGetAllService petGetAllService,
                      PetCreateService petCreateService,
                      PetUpdateService petUpdateService,
                      PetDeleteService petDeleteService,
                      PetGetByIdService petGetByIdService,
                      PetGetCommandsById petGetCommandsById,
                      PetTrainCommand petTrainCommand) {
        this.petGetAllService = petGetAllService;
        this.petCreateService = petCreateService;
        this.petUpdateService = petUpdateService;
        this.petDeleteService = petDeleteService;
        this.petGetByIdService = petGetByIdService;
        this.petGetCommandsById = petGetCommandsById;
        this.petTrainCommand = petTrainCommand;
        this.createPet = new CreatePet();
        this.viewService = new DataViewService();
    }

    public void createPet(PetType type) {
        byte bytes[] = viewService.getName().getBytes(StandardCharsets.UTF_8);
        String right_name = new String(bytes, StandardCharsets.UTF_8);
        String[] data = new String[] { right_name, viewService.getBirthday()};
        int typeNumber = PetType.getTypeNumber(type);
        //validator.validate(data);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthday = LocalDate.parse(data[1], formatter);
        try {
            int res = petCreateService.create(createPet.creator(1, type, data[0], birthday, typeNumber));
            viewService.showMessage(String.format("%d Запись успешно добавлена", res));
        } catch (RuntimeException e) {
            viewService.showMessage(e.getMessage());
        }

    }

    public void getAllPet() {
        try {
            viewService.printAll(petGetAllService.getAll());
        } catch (RuntimeException e) {
            viewService.showMessage(e.getMessage());
        }
    }

    public void delete(Integer petID) {
        try {
            petDeleteService.delete(petID);
            viewService.showMessage("Запись успешно удалена");
        } catch (RuntimeException e) {
            viewService.showMessage(e.getMessage());
        }
    }

    public Pet getById(int id) {
        try {
            return petGetByIdService.getById(id);
        } catch (RuntimeException e) {
            viewService.showMessage(e.getMessage());
        }
        return null;
    }

    public void getCommands(int id) {
        try {
            viewService.printComs(petGetCommandsById.getCommandsById(id));
        } catch (RuntimeException e) {
            viewService.showMessage(e.getMessage());
        }
    }

    public boolean trainPet(int id, String command) {
        try {
            if (petGetCommandsById.getCommandsById(id).contains(command))
                viewService.showMessage("\nЭто команда уже изучена!");
            else {
                petTrainCommand.train(id, command);
                viewService.showMessage("Команда изучена!\n");
                return true;
            }
        } catch (RuntimeException e) {
            viewService.showMessage(e.getMessage());
        }
        return false;
    }

    public void updatePet(int id) {
        Pet pet = getById(id);
        String data[] = new String[] {viewService.getName(), viewService.getBirthday()};

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthday = LocalDate.parse(data[1], formatter);
        pet.setName(data[0]);
        pet.setBirthday(birthday);

        try {
            int res = petUpdateService.update(pet);
            viewService.showMessage(String.format("%d Запись успешно изменена! \n", res));
        } catch (RuntimeException e) {
            viewService.showMessage(e.getMessage());
        }
    }
}
