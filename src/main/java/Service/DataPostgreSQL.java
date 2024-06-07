package Service;

public class DataPostgreSQL {
    private String url = "jdbc:postgresql://localhost:5432/human_friends";
    private String user = "postgres";
    private String password = "postgres";

    public DataPostgreSQL() {}

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
