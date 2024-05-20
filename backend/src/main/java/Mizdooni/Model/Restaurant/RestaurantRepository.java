package Mizdooni.Model.Restaurant;

import Mizdooni.Model.Constants;
import Mizdooni.Model.HibernateUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static Mizdooni.Model.Constants.RESTAURANTS_TABLE_NAME;

public class RestaurantRepository {
    private static RestaurantRepository instance;
    private ArrayList<Restaurant> restaurants = new ArrayList<>();

    private RestaurantDAO dao = new RestaurantDAO();

    public RestaurantRepository() throws Exception {
        if(!dao.checkTableExistence(RESTAURANTS_TABLE_NAME)){
            restaurants = dao.fetchFromAPI(Constants.GET_RESTAURANTS_URL, Restaurant.class);
            for (Restaurant rest:restaurants) {
                dao.addToDatabase(rest);
            }
        }
    }

    public static RestaurantRepository getInstance() throws Exception {
        if(instance == null)
            return new RestaurantRepository();
        else return instance;
    }

    public ArrayList<Restaurant> getAll(String location, String name, String type) throws SQLException {
        ArrayList fields = new ArrayList<>();
        ArrayList values = new ArrayList<>();

        if(location != null && location != ""){
            values.add(location);
            fields.add("address");
        }if(name != null && name != ""){
            values.add(name);
            fields.add("name");
        }if(type != null && type != ""){
            values.add(type);
            fields.add("type");
        }
        System.out.println("restaurant values; ####" + values);
        System.out.println("restaurant fields;####" + fields);

        System.out.println("findByFields begin");
        Connection conn = HibernateUtils.getConnection();
        System.out.println("after getting conn");

        if(values.size() == 0) return dao.getAll();
        else return dao.findByFields(conn, values, fields, RESTAURANTS_TABLE_NAME);
    }

    public void addRestaurant(Restaurant newRest) throws SQLException {
        dao.addToDatabase(newRest);
    }

    public ArrayList<Restaurant> findRestaurantsByManager(String username) throws SQLException {
        System.out.println("findByFields begin");
        Connection conn = HibernateUtils.getConnection();
        System.out.println("after getting conn");
        return dao.findByFields(conn, Arrays.asList(username),
                Arrays.asList("managerUsername"), RESTAURANTS_TABLE_NAME);
    }

    public ArrayList<Restaurant> findTopRestaurants(int number) {
        return dao.findTopRate(number);
    }
}
