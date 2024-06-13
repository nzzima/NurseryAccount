package Model;

import java.time.LocalDate;

public class CreatePet {
    public Pet creator (Integer id, PetType type, String name, LocalDate birthday, Integer genusID) {
        Pet pet = createNew(type);
        pet.setPet_id(id);
        pet.setName(name);
        pet.setBirthday(birthday);
        pet.setGenus_id(genusID);
        return pet;
    }
    private Pet createNew (PetType type) {
        switch (type) {
            case Cat -> {
                return new Cat();
            }
            case Dog -> {
                return new Dog();
            }
            case Hamster -> {
                return new Hamster();
            }
        }
        return null;
    }
}
