package semashibaev.ifmo.server;

import semashibaev.ifmo.server.structCollection.Movie;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class DataBase {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";//localhost на pg, postgres на studs
    private static final String USER = "myuser";
    private static final String PASSWORD = "lip807";
    public Connection connection;
    private static final String USERS_TABLE = "S284697_USERS_MOVIE_TABLE";
    static final String USERS_INIT = "CREATE TABLE IF NOT EXISTS " + USERS_TABLE + "(\n" +
            "    id serial PRIMARY KEY, \n" +
            "    username VARCHAR(512), \n" +
            "    pass VARCHAR(512) \n" +
            ");";
    private static volatile DataBase instance;
    private static final String COLLECTION_TABLE = "S284697_COLLECTION_MOVIES_TABLE";
    private static final String COLLECTION_INIT = "CREATE TABLE IF NOT EXISTS " + COLLECTION_TABLE + "(\n" +
            "    id serial PRIMARY KEY, \n" +
            "    name text,\n" +
            "    coordinates_x real, \n" +
            "    coordinates_y real, \n" +
            "    creationDate text, \n" +
            "    oscarsCount real, \n" +
            "    goldenPalmCount real, \n" +
            "    totalBoxOffice real, \n" +
            "    mpaaRating text, \n" +
            "    screenwriter text, \n" +
            "    owner text \n" +
            ");";


    private DataBase() throws SQLException, ClassNotFoundException {
        connect();
    }

    public static DataBase getInstance() throws SQLException, ClassNotFoundException {
        DataBase result = instance;
        if (result != null)
            return result;
        synchronized (DataBase.class) {
            if (instance == null)
                instance = new DataBase();
            return instance;
        }
    }

    public void connect() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        connection = DriverManager.getConnection(URL, USER, PASSWORD);

        Statement init1 = connection.createStatement();
        init1.execute(USERS_INIT);

        Statement init2 = connection.createStatement();
        init2.execute(COLLECTION_INIT);

    }

    public boolean addMovie(Movie m) {
        try {
            String SQL = "INSERT INTO " + COLLECTION_TABLE +
                    "(name, coordinates_x, coordinates_y, creationDate, oscarsCount , goldenPalmCount , totalBoxOffice,mpaaRating,screenwriter,owner)"+
                    " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;";
            PreparedStatement stIns = connection.prepareStatement(SQL);
            stIns.setString(1, m.getName());
            stIns.setLong(2, m.getCoordinates().getX());
            stIns.setLong(3, m.getCoordinates().getY());
            stIns.setString(4, m.getCreationDate().toString());
            stIns.setInt(5, m.getOscarssCount());
            stIns.setInt(6, m.getGoldenPalmCount());
            stIns.setDouble(7, m.getTotalBoxOffice());
            if (m.getmpaaRating() != null)
                stIns.setString(8, m.getmpaaRating().toString());
            else
                stIns.setString(8, "");
            if (m.getPerson() != null)
                stIns.setString(9, m.getPerson().getName());
            else
                stIns.setString(9, "");
            stIns.setString(10, m.getOwner());
            ResultSet rs = stIns.executeQuery();
            rs.next();
            m.setId(rs.getInt("id"));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public ResultSet getCollection() {
        String SQL = "SELECT * FROM " + COLLECTION_TABLE + ";";
        try {
            Statement stIns = connection.createStatement();
            return stIns.executeQuery(SQL);
        } catch (Exception e) {
            return null;
        }

    }

    public boolean deleteMovie(Movie m) {
        try {
            Statement stIns = connection.createStatement();
            String cmd = "DELETE FROM " + COLLECTION_TABLE + "\n" +
                    "WHERE id = " + m.getId() + ";";
            stIns.execute(cmd);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean deleteCollection(String username) {
        try {
            Statement stIns = connection.createStatement();
            String cmd = "DELETE FROM " + COLLECTION_TABLE + "\nWHERE owner = \'" + username + "\';";
            stIns.execute(cmd);
        } catch (Exception e) {
            return false;
        }
        return true;

    }

    public boolean insertUser(String username, String password) {
        try {
            Statement stIns = connection.createStatement();
            String cmd = "INSERT INTO " + USERS_TABLE + "(username, pass)" +
                    " VALUES(" + "\'" + username + "\',"
                    + "\'" + password + "\');";
            stIns.execute(cmd);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public ResultSet getUsers() {
        String SQL = "SELECT * FROM " + USERS_TABLE + ";";
        try {
            Statement stIns = connection.createStatement();
            return stIns.executeQuery(SQL);
        } catch (Exception e) {
            return null;
        }
    }

}


