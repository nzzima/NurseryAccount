package Service;

import Model.CreatePet;
import Model.Pet;
import Model.PetType;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataPetGetByIdService implements PetGetByIdService{
    private String SQLstr;
    private ResultSet resultSet;
    private CreatePet createPet;

    public DataPetGetByIdService() {
        this.createPet = new CreatePet();
    }

    @Override
    public Pet getById(Integer petId) {
        Pet pet = null;
        DataPostgreSQL postgreSQLmanager = new DataPostgreSQL();
        try (Connection con = DriverManager.getConnection(postgreSQLmanager.getUrl(),
                postgreSQLmanager.getUser(),
                postgreSQLmanager.getPassword())) {
            SQLstr = "SELECT id, name, birthday, genus_id FROM pets WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(SQLstr);
            statement.setInt(1, petId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                LocalDate birthday = resultSet.getDate(3).toLocalDate();
                Integer genus_id = resultSet.getInt(4);
                PetType type = PetType.getType(resultSet.getInt(4));

                pet = createPet.creator(id, type, name, birthday, genus_id);
            }
            return pet;
        } catch (SQLException exception) {
            Logger.getLogger(DataPetGetAllService.class.getName()).log(Level.SEVERE, null, exception);
            throw new RuntimeException(exception.getMessage());
        }
    }
}
