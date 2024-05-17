package Mizdooni.Model.Restaurant;

import Mizdooni.Model.Constants;

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
        List<String> values = Collections.emptyList();
        List<String> fields = Collections.emptyList();
        if(location != null){
            values.add(location);
            fields.add("address");
        }if(name != null){
            values.add(name);
            fields.add("name");
        }if(name != null){
            values.add(type);
            fields.add("type");
        }
        return dao.findByFields(values, fields, RESTAURANTS_TABLE_NAME);
    }

    public void addRestaurant(Restaurant newRest) throws SQLException {
        dao.addToDatabase(newRest);
    }

    public ArrayList<Restaurant> findRestaurantsByManager(String username) throws SQLException {
        return dao.findByFields(Arrays.asList(username),
                Arrays.asList("managerUsername"), RESTAURANTS_TABLE_NAME);
    }

    public ArrayList<Restaurant> findTopRestaurants(int number) {
        return dao.findTopRate(number);
    }
}
