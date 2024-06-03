import Controller.Controller;
import Model.Pet;
import Service.DataPetDatabaseService;
import Service.PetDatabaseService;
import View.ConsoleMenu;

public class Main {
    public static void main(String[] args) {
        PetDatabaseService <Pet> farm = new DataPetDatabaseService();
        Controller controller = new Controller(farm);
        try {
            new ConsoleMenu(controller).start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
