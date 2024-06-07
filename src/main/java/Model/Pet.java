package Model;

import java.time.LocalDate;

public abstract class Pet {
    protected Integer pet_id;
    protected String name;
    protected LocalDate birthday;
    protected Integer genus_id;


    public Integer getGenus_id() {
        return genus_id;
    }

    public void setGenus_id(Integer genus_id) {
        this.genus_id = genus_id;
    }

    public Integer getPet_id() {
        return pet_id;
    }

    public void setPet_id(Integer pet_id) {
        this.pet_id = pet_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "pet_id=" + pet_id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", genus_id=" + genus_id +
                '}';
    }
}
