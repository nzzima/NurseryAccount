package Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataPetDeleteService implements PetDeleteService {
    private String SQLstr;
    @Override
    public void delete(Integer petID) {
        DataPostgreSQL postgreSQLmanager = new DataPostgreSQL();
        try (Connection con = DriverManager.getConnection(postgreSQLmanager.getUrl(),
                postgreSQLmanager.getUser(),
                postgreSQLmanager.getPassword())) {
            SQLstr = "DELETE FROM pets WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(SQLstr);

            statement.setInt(1, petID);
            statement.executeUpdate();
        } catch (SQLException exception) {
            Logger.getLogger(DataPetGetAllService.class.getName()).log(Level.SEVERE, null, exception);
            throw new RuntimeException(exception.getMessage());
        }
    }
}
