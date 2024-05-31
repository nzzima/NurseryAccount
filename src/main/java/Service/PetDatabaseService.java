package Service;

import java.util.List;

public interface PetDatabaseService <T> {
    List<T> getAll();
    T getById(Integer petId);
    Integer create(T pet);
    Integer update(T pet);
    void delete(Integer id);
}
