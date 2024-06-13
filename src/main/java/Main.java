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
        Controller controller = new Controller(getAllService, createService, updateService, deleteService, getByIdService);
        new Menu(controller).start();
    }
}
