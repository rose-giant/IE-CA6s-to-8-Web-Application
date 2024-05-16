package Mizdooni.Model.User;

import Mizdooni.Model.Address;
import Mizdooni.Model.Constants;

import java.sql.SQLException;
import java.util.ArrayList;
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
        if(instance == null)
            return new UserRepository();
        else return instance;
    }

    public ArrayList<User> getAll() throws SQLException {
        return dao.getAll();
    }

    public User findUserByUserName(String userName) {
        for(User user: users) {
            if(Objects.equals(user.username, userName)) {
                return user;
            }
        }
        return null;
    }


    public User findByUsernameAndPassword(String username, String password) throws SQLException {
         User u1 = dao.findByField(username, "username", CLIENTS_TABLE_NAME);
         User u2 = dao.findByField(username, "username", MANAGERS_TABLE_NAME);
         if(u1 != null && Objects.equals(u1.getPassword() , password))return u1;
         else if(u2 != null && Objects.equals(u2.getPassword() , password))return u2;
         else return null;
    }

    public void addUser(User newUser) throws SQLException {
        dao.addToDatabase(newUser);
    }

}