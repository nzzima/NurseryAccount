package Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataPetTrainCommand implements PetTrainCommand{
    private String SQLstr;
    @Override
    public void train(int id, String command) {
        DataPostgreSQL postgreSQLmanager = new DataPostgreSQL();
        try (Connection con = DriverManager.getConnection(postgreSQLmanager.getUrl(),
                postgreSQLmanager.getUser(),
                postgreSQLmanager.getPassword())) {
            SQLstr = "INSERT INTO pet_command (pet_id, command_id) SELECT ?, (SELECT id_command FROM commands " +
                    "WHERE command_name = ?)";
            PreparedStatement statement = con.prepareStatement(SQLstr);
            statement.setInt(1, id);
            statement.setString(2, command);

            statement.executeUpdate();
        } catch (SQLException exception) {
            Logger.getLogger(DataPetGetAllService.class.getName()).log(Level.SEVERE, null, exception);
            throw new RuntimeException(exception.getMessage());
        }
    }
}
