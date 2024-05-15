package Mizdooni.Model.Restaurant;

import Mizdooni.Model.Constants;
import Mizdooni.Model.Restaurant.Restaurant;
import Mizdooni.Model.Restaurant.RestaurantDAO;
import Mizdooni.Model.Restaurant.RestaurantRepository;
import Mizdooni.Model.User.User;

import java.util.ArrayList;
import java.util.Objects;

import static Mizdooni.Model.Constants.RESTAURANTS_TABLE_NAME;

public class RestaurantRepository {
    private static RestaurantRepository instance;
    private ArrayList<Restaurant> restaurants = new ArrayList<>();

    private RestaurantDAO dao = new RestaurantDAO();

    public RestaurantRepository() throws Exception {
        dao.createTable(RESTAURANTS_TABLE_NAME);
        restaurants = dao.fetchFromAPI(Constants.GET_RESTAURANTS_URL, Restaurant.class);
        for (Restaurant rest:restaurants) {
            dao.addToDatabase(rest);
        }
    }

    public static RestaurantRepository getInstance() throws Exception {
        if(instance == null)
            return new RestaurantRepository();
        else return instance;
    }

    public ArrayList<Restaurant> getAll() {
        return restaurants;
    }
}
