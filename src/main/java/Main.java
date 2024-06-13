import Controller.Controller;
import Service.*;
import View.Menu;

public class Main {
    public static void main(String[] args) {
        PetCreateService createService = new DataPetCreateService();
        PetDeleteService deleteService = new DataPetDeleteService();
        PetGetAllService getAllService = new DataPetGetAllService();
        PetGetByIdService getByIdService = new DataPetGetByIdService();
        PetUpdateService updateService = new DataPetUpdateService();
        PetGetCommandsById getCommandsById = new DataPetGetCommandsById();
        PetTrainCommand trainCommand = new DataPetTrainCommand();
        Controller controller = new Controller(getAllService, createService, updateService, deleteService, getByIdService, getCommandsById, trainCommand);
        new Menu(controller).start();
    }
}
