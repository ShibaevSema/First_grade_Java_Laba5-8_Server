package semashibaev.ifmo.server;

import semashibaev.ifmo.cfs.CommandForServer;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class NetGuard {
    private HashMap<String, String> users = new HashMap<>();
    private DataBase db;

    NetGuard() throws SQLException, ClassNotFoundException {
        this.db = DataBase.getInstance();
        syncWithDb();
    }

    private static String encryptPass(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized boolean auth(CommandForServer commandForServer) {
        String username = commandForServer.getUsername();
        String password = encryptPass(commandForServer.getPassword());
        if (users.containsKey(username)) {
            return users.get(username).equals(password);
        } else {
            this.users.put(username, password);
            return this.db.insertUser(username, password);
        }
    }

    private void syncWithDb() throws SQLException {
        ResultSet rs = this.db.getUsers();
        while (rs.next())
            this.users.put(rs.getString("username"), rs.getString("pass"));
    }
}


