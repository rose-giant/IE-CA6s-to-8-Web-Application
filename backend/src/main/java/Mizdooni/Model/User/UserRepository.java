package Mizdooni.Model.User;

import Mizdooni.Model.Address;
import Mizdooni.Model.Constants;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static Mizdooni.Model.Constants.*;

public class UserRepository {

    private static UserRepository instance;
    private ArrayList<User> users = new ArrayList<>();
    UserDAO dao = new UserDAO();

    public UserRepository() throws Exception {
        boolean a = dao.checkTableExistence(MANAGER_ROLE);
        boolean b = dao.checkTableExistence(CLIENT_ROLE);
        if(!a || !b){
            users = dao.fetchFromAPI(Constants.GET_USERS_URL, User.class);
            for (User user:users) {        
                dao.addToDatabase(user);
            }
        }

    }

    public static UserRepository getInstance() throws Exception {
        if(instance == null) {
            return new UserRepository();
        }
        else {
            return instance;
        }
    }

    public ArrayList<User> getAll() throws SQLException {
        return dao.getAll();
    }

    public User findUserByUserName(String username) throws SQLException {
        ArrayList<User> u1 = dao.findByFields(Arrays.asList(username),Arrays.asList("username") , CLIENTS_TABLE_NAME);
        if(!u1.isEmpty()) return u1.get(0);
        ArrayList<User> u2 = dao.findByFields(Arrays.asList(username),Arrays.asList("username") , MANAGERS_TABLE_NAME);
        if(!u2.isEmpty()) return u2.get(0);
        else return null;
    }

    public User findByUsernameAndPassword(String username, String password) throws SQLException {
         ArrayList<User> u1 = dao.findByFields(Arrays.asList(username, password),Arrays.asList("username", "password") , CLIENTS_TABLE_NAME);
         if(!u1.isEmpty()) return u1.get(0);
         ArrayList<User> u2 = dao.findByFields(Arrays.asList(username, password),Arrays.asList("username", "password") , MANAGERS_TABLE_NAME);
         if(!u2.isEmpty()) return u2.get(0);
         else return null;
    }

    public void addUser(User newUser) throws SQLException {
        dao.addToDatabase(newUser);
    }

}