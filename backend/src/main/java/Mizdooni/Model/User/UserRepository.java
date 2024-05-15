package Mizdooni.Model.User;

import Mizdooni.Model.Address;
import Mizdooni.Model.Constants;

import java.util.ArrayList;
import java.util.Objects;

public class UserRepository {

    private static UserRepository instance;
    private ArrayList<User> users = new ArrayList<>();
    UserDAO dao = new UserDAO();

    public UserRepository() throws Exception {
        dao.createTable(Constants.MANAGER_ROLE);
        dao.createTable(Constants.CLIENT_ROLE);
        users = dao.fetchFromAPI(Constants.GET_USERS_URL, User.class);
        for (User user:users) {
            dao.addToDatabase(user);
        }

    }

    public static UserRepository getInstance() throws Exception {
        if(instance == null)
            return new UserRepository();
        else return instance;
    }

    public ArrayList<User> getAll() {
        return users;
    }

    public User findUserByUserName(String userName) {
        for(User user: users) {
            if(Objects.equals(user.username, userName)) {
                return user;
            }
        }
        return null;
    }

    public User findByUsernameAndPassword(String username, String password) {
        for(User user: users) {
            if(Objects.equals(user.username, username) && Objects.equals(user.password, password)) {
                return user;
            }
        }
        return null;
    }

    public void addUser(User newUser) {
        users.add(newUser);
    }
}