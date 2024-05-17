package Mizdooni.Model.Table;

import Mizdooni.Model.Constants;
import Mizdooni.Model.Review.Review;
import Mizdooni.Model.User.User;

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
        if(!dao.checkTableExistence(TABLES_TABLE_NAME)){
            tables = dao.fetchFromAPI(Constants.GET_TABLES_URL, TableRest.class);
            for (TableRest user:tables) {
                dao.addToDatabase(user);
            }
        }
    }

    public static TableRepository getInstance() throws Exception {
        if(instance == null)
            return new TableRepository();
        else return instance;
    }

    public ArrayList<TableRest> getAll() throws SQLException {
        return dao.getAll();
    }

    public ArrayList<TableRest> findTablesByRestaurantName(String restaurantName) throws SQLException {
        return dao.findByFields(Arrays.asList(restaurantName), Arrays.asList("tableRestaurant"), TABLES_TABLE_NAME);
    }

    public void addTable(TableRest newRest) throws SQLException {
        dao.addToDatabase(newRest);
    }
}
