package Mizdooni.Model.Table;

import Mizdooni.Model.Constants;
import Mizdooni.Model.HibernateUtils;
import Mizdooni.Model.Review.Review;
import Mizdooni.Model.User.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static Mizdooni.Model.Constants.REVIEWS_TABLE_NAME;
import static Mizdooni.Model.Constants.TABLES_TABLE_NAME;

public class TableRepository {
    private static TableRepository instance;
    private ArrayList<TableRest> tables = new ArrayList<>();

    TableDAO dao = new TableDAO();

    public TableRepository() throws Exception {
//        if(!dao.checkTableExistence(TABLES_TABLE_NAME)){
//            tables = dao.fetchFromAPI(Constants.GET_TABLES_URL, TableRest.class);
//            for (TableRest user:tables) {
//               dao.addToDatabase(user);
//            }
//        }
//        tables = dao.fetchFromAPI(Constants.GET_TABLES_URL, TableRest.class);
        tables = dao.getAll();
    }

    public static TableRepository getInstance() throws Exception {
        if(instance == null)
            instance = new TableRepository();
        return instance;
    }

    public ArrayList<TableRest> getAll() throws SQLException {
//        return dao.getAll();
        return tables;
    }

    public ArrayList<TableRest> findTablesByRestaurantName(String restaurantName) throws SQLException {
//        System.out.println("findByFields begin");
//        Connection conn = HibernateUtils.getConnection();
//        System.out.println("after getting conn");
//        return dao.findByFields(conn, Arrays.asList(restaurantName), Arrays.asList("tableRestaurant"), TABLES_TABLE_NAME);
        ArrayList<TableRest> ts = new ArrayList<>();
        for (TableRest user: tables) {
            if(user.restaurantName.equals(restaurantName)) ts.add(user);
        }
        return ts;
    }

    public TableRest findTableByTableNumber(int tableNumber) throws SQLException {
        for (TableRest user: tables) {
            if(user.tableNumber == tableNumber) return user;
        }
        return null;
    }

    public void addTable(TableRest newRest) throws SQLException {
//        dao.addToDatabase(newRest);
        tables.add(newRest);
    }

}
