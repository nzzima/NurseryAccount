package View;

import Model.Pet;

import java.util.List;

public interface ViewService {
    String getName();
    String getBirthday();
    void printAll(List<Pet> list);
    void printComs(List<String> list);
    void showMessage(String message);

}
