package Mizdooni.Model.User;

import Mizdooni.Model.Address;
import Mizdooni.Model.Constants;
import Mizdooni.Model.HibernateUtils;

import java.sql.Connection;
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
//        boolean a = dao.checkTableExistence(MANAGER_ROLE);
//        boolean b = dao.checkTableExistence(CLIENT_ROLE);
//        if(!a || !b){
//            users = dao.fetchFromAPI(Constants.GET_USERS_URL, User.class);
//            for (User user:users) {
//                dao.addToDatabase(user);
//            }
//        }

//        users = dao.fetchFromAPI(Constants.GET_USERS_URL, User.class);
        users = dao.getAll();
    }

    public static UserRepository getInstance() throws Exception {
        if(instance == null) instance = new UserRepository();
        return instance;
    }

    public ArrayList<User> getAll() throws SQLException {
        return users;
    }

    public User findUserByUserName(String username)  {
//        System.out.println("findByFields begin");
//        Connection conn = HibernateUtils.getConnection();
//        System.out.println("after getting conn");
//        ArrayList<User> u1 = dao.findByFields(conn, Arrays.asList(username),Arrays.asList("username") , CLIENTS_TABLE_NAME);
//        if(u1.size() != 0) return u1.get(0);
//        System.out.println("findByFields begin");
//        Connection conn2 = HibernateUtils.getConnection();
//        System.out.println("after getting conn");
//        ArrayList<User> u2 = dao.findByFields(conn2, Arrays.asList(username),Arrays.asList("username") , MANAGERS_TABLE_NAME);
//        if(u2.size() != 0) return u2.get(0);
//        else return null;
        for (User user: users) {
            if(user.username.equals(username)) return user;
        }
        return null;
    }


    public User findByUsernameAndPassword(String username, String password) {
        for (User user: users) {

            if(user.username.equals(username) && user.password.equals(password)) return user;
        }
        return null;
    }

    public void addUser(User newUser) throws SQLException {
//        dao.addToDatabase(newUser);
        users.add(newUser);
    }

}