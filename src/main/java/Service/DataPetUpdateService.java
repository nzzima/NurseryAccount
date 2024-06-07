package Service;

import Model.Pet;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataPetUpdateService implements PetUpdateService{
    private String SQLstr;
    @Override
    public Integer update(Pet pet) {
        DataPostgreSQL postgreSQLmanager = new DataPostgreSQL();
        Integer rows = null;
        try (Connection con = DriverManager.getConnection(postgreSQLmanager.getUrl(),
                postgreSQLmanager.getUser(),
                postgreSQLmanager.getPassword())) {

            SQLstr = "UPDATE pets SET name = ?, birthday = ? WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(SQLstr);

            statement.setString(1, pet.getName());
            statement.setDate(2, Date.valueOf(pet.getBirthday()));
            statement.setInt(3, pet.getPet_id());

            rows = statement.executeUpdate();
            return rows;

        } catch (SQLException exception) {
            Logger.getLogger(DataPetGetAllService.class.getName()).log(Level.SEVERE, null, exception);
            throw new RuntimeException(exception.getMessage());
        }
    }
}
