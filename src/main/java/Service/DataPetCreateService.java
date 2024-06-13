package Service;

import Model.Pet;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataPetCreateService implements PetCreateService{
    private String SQLstr;
    @Override
    public Integer create(Pet pet) {
        DataPostgreSQL postgreSQLmanager = new DataPostgreSQL();
        Integer rows = null;
        try (Connection con = DriverManager.getConnection(postgreSQLmanager.getUrl(),
                postgreSQLmanager.getUser(),
                postgreSQLmanager.getPassword())) {
            SQLstr = "INSERT INTO pets (name, birthday, genus_id) SELECT ?, ?, ?";
            PreparedStatement statement = con.prepareStatement(SQLstr);

            statement.setString(1, pet.getName());
            statement.setDate(2, Date.valueOf(pet.getBirthday()));
            statement.setInt(3, pet.getGenus_id());

            rows = statement.executeUpdate();
            return rows;

        } catch (SQLException exception) {
            Logger.getLogger(DataPetGetAllService.class.getName()).log(Level.SEVERE, null, exception);
            throw new RuntimeException(exception.getMessage());
        }
    }
}
