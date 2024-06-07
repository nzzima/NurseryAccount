package Service;

import Model.Pet;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataPetGetAllService implements PetGetAllService{
    private  String SQLstr;
    private  ResultSet resultSet;

    @Override
    public List<Pet> getAll() {
        DataPostgreSQL postgreSQLmanager = new DataPostgreSQL();
        List<Pet> pets_data = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(postgreSQLmanager.getUrl(),
                                                          postgreSQLmanager.getUser(),
                                                          postgreSQLmanager.getPassword())) {
            SQLstr = "SELECT name, birthday, genus_id FROM pets";
            PreparedStatement statement = con.prepareStatement(SQLstr);
            resultSet = statement.executeQuery();
            int pet_id = 1;
            while (resultSet.next()) {
                String name = resultSet.getString(1);
                LocalDate birthday = resultSet.getDate(2).toLocalDate();
                Integer genus_id = resultSet.getInt(3);

                //Необходим класс для создания нового животного

                pet_id += 1;
            }
            return pets_data;
        } catch (SQLException exception) {
            Logger.getLogger(DataPetGetAllService.class.getName()).log(Level.SEVERE, null, exception);
            throw new RuntimeException(exception.getMessage());
        }
    }
}
