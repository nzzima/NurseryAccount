package Service;
import Model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataPetDatabaseService implements PetDatabaseService<Pet> {
    private static String url = "jdbc:postgresql://localhost:5432/postgres";
    private static String user = "postgres";
    private static String password = "postgres";
    private Creator creator;
    private Statement statement;
    private String SQLstr;
    private ResultSet resultSet;

    public DataPetDatabaseService() {
        this.creator = new PetCreator();
    }

    @Override
    public List getAll() {
        List<Pet> farm = new ArrayList<Pet>();
        Pet pet;
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            SQLstr = "SELECT GenusId, Id, PetName, Birthday FROM pet_list ORDER BY Id";
            PreparedStatement statement = con.prepareStatement(SQLstr);
            resultSet = statement.executeQuery(SQLstr);
            while (resultSet.next()) {
                PetType type = PetType.getType(resultSet.getInt(1));
                Integer id = resultSet.getInt(2);
                String name = resultSet.getString(3);
                LocalDate birthday = resultSet.getDate(4).toLocalDate();

                pet = creator.createPet(type, name, birthday);
                pet.setPet_id(id);
                farm.add(pet);

            }
            return farm;
        } catch (SQLException exception) {
            Logger.getLogger(DataPetDatabaseService.class.getName()).log(Level.SEVERE, null, exception);
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public Pet getById(Integer petId) {
        Pet pet = null;
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            SQLstr = "SELECT GenusId, Id, PetName, Birthday FROM pet_list WHERE Id = ?";
            PreparedStatement prepSt = con.prepareStatement(SQLstr);
            prepSt.setInt(1, petId);
            resultSet = prepSt.executeQuery();

            if (resultSet.next()) {
                PetType type = PetType.getType(resultSet.getInt(1));
                int id = resultSet.getInt(2);
                String name = resultSet.getString(3);
                LocalDate birthday = resultSet.getDate(4).toLocalDate();

                pet = creator.createPet(type, name, birthday);
                pet.setPet_id(id);
            }
            return pet;
        } catch (ClassNotFoundException | IOException | SQLException exception) {
            Logger.getLogger(DataPetDatabaseService.class.getName()).log(Level.SEVERE, null, exception));
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public Integer create(Pet pet) {
        Integer rows = null;
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            SQLstr = "INSERT INTO pet_list (PetName, Birthday, GenusId) SELECT ?, ?, (SELECT Id FROM pet_types WHERE Genus_name = ?)";
            PreparedStatement prepSt = con.prepareStatement(SQLstr);

            prepSt.setString(1, pet.getName());
            prepSt.setDate(2, Date.valueOf(pet.getBirthday()));
            prepSt.setString(3, pet.getClass().getSimpleName());

            rows = prepSt.executeUpdate();
            return rows;
        } catch (ClassNotFoundException | IOException | SQLException exception) {
            Logger.getLogger(DataPetDatabaseService.class.getName()).log(Level.SEVERE, null, exception));
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public Integer update(Pet pet) {
        Integer rows;
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            SQLstr = "UPDATE pet_list SET PetName = ?, Birthday = ? WHERE Id = ?";
            PreparedStatement prepSt = con.prepareStatement(SQLstr);

            prepSt.setString(1, pet.getName());
            prepSt.setDate(2, Date.valueOf(pet.getBirthday()));
            prepSt.setInt(3, pet.getPet_id());

            rows = prepSt.executeUpdate();
            return rows;
        } catch (ClassNotFoundException | IOException | SQLException exception) {
            Logger.getLogger(DataPetDatabaseService.class.getName()).log(Level.SEVERE, null, exception));
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            SQLstr = "DELETE FROM pet_list WHERE Id = ?";
            PreparedStatement prepSt = con.prepareStatement(SQLstr);

            prepSt.setInt(1, id);

            prepSt.executeUpdate();
        } catch (ClassNotFoundException | IOException | SQLException exception) {
            Logger.getLogger(DataPetDatabaseService.class.getName()).log(Level.SEVERE, null, exception));
            throw new RuntimeException(exception.getMessage());
        }
    }

    public void train(Integer id, String command) {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            String SQLstr = "INSERT INTO pet_command (PetId, CommandId) SELECT ?, (SELECT Id FROM commands WHERE Command_name = ?)";
            PreparedStatement prepSt = con.prepareStatement(SQLstr);
            prepSt.setInt(1, id);
            prepSt.setString(2, command);

            prepSt.executeUpdate();
        } catch (ClassNotFoundException | IOException | SQLException exception) {
            Logger.getLogger(DataPetDatabaseService.class.getName()).log(Level.SEVERE, null, exception));
            throw new RuntimeException(exception.getMessage());
        }
    }

    public List<String> getCommandsById (int pet_id, int commands_type) {
        List <String> commands = new ArrayList <>();
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            if (commands_type == 1){
                SQLstr = "SELECT Command_name FROM pet_command pc " +
                        "JOIN commands c ON pc.CommandId = c.Id WHERE pc.PetId = ?";
            } else {
                SQLstr = "SELECT Command_name FROM commands c " +
                        "JOIN Genus_command gc ON c.Id = gc.CommandId " +
                        "WHERE gc.GenusId = (SELECT GenusId FROM pet_list WHERE Id = ?)";
            }
            PreparedStatement prepSt = con.prepareStatement(SQLstr);
            prepSt.setInt(1, pet_id);
            resultSet = prepSt.executeQuery();

            while (resultSet.next()) {
                commands.add(resultSet.getString(1));
            }

            return commands;
        } catch (ClassNotFoundException | IOException | SQLException exception) {
            Logger.getLogger(DataPetDatabaseService.class.getName()).log(Level.SEVERE, null, exception));
            throw new RuntimeException(exception.getMessage());
        }
    }
}