package Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataPetGetCommandsById implements PetGetCommandsById{
    private String SQLstr;
    private ResultSet resultSet;
    @Override
    public List<String> getCommandsById(int petID) {
        List <String> commands = new ArrayList<>();
        DataPostgreSQL postgreSQLmanager = new DataPostgreSQL();
        try (Connection con = DriverManager.getConnection(postgreSQLmanager.getUrl(),
                postgreSQLmanager.getUser(),
                postgreSQLmanager.getPassword())) {
            SQLstr = "SELECT command_name FROM commands join pet_command on commands.id_command = pet_command.command_id " +
                    "WHERE pet_command.pet_id = ?";
            PreparedStatement statement = con.prepareStatement(SQLstr);
            statement.setInt(1, petID);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                commands.add(resultSet.getString(1));
            }
            return commands;
        } catch (SQLException exception) {
            Logger.getLogger(DataPetGetAllService.class.getName()).log(Level.SEVERE, null, exception);
            throw new RuntimeException(exception.getMessage());
        }
    }
}
