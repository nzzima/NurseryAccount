package Service;

import Model.CreatePet;
import Model.Pet;
import Model.PetType;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataPetGetAllService implements PetGetAllService{
    private String SQLstr;
    private ResultSet resultSet;
    private CreatePet createPet;

    public DataPetGetAllService() {
        this.createPet = new CreatePet();
    }

    @Override
    public List<Pet> getAll() {
        DataPostgreSQL postgreSQLmanager = new DataPostgreSQL();
        List<Pet> pets_data = new ArrayList<>();
        Pet pet;
        try (Connection con = DriverManager.getConnection(postgreSQLmanager.getUrl(),
                                                          postgreSQLmanager.getUser(),
                                                          postgreSQLmanager.getPassword())) {
            SQLstr = "SELECT id, name, birthday, genus_id FROM pets";
            PreparedStatement statement = con.prepareStatement(SQLstr);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                LocalDate birthday = resultSet.getDate(3).toLocalDate();
                Integer genus_id = resultSet.getInt(4);
                PetType type = PetType.getType(resultSet.getInt(4));

                pet = createPet.creator(id, type, name, birthday, genus_id);
                pets_data.add(pet);

            }
            return pets_data;
        } catch (SQLException exception) {
            Logger.getLogger(DataPetGetAllService.class.getName()).log(Level.SEVERE, null, exception);
            throw new RuntimeException(exception.getMessage());
        }
    }
}
