package View;

import java.util.List;

public interface View <T> {
    String getName();
    String getBirthday();
    void printAll (List list, Class clazz);
    void showMessage (String message);
}
