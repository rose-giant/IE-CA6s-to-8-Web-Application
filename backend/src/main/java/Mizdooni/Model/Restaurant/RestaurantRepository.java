package Mizdooni.Model.Restaurant;

import Mizdooni.Model.Constants;

import java.util.ArrayList;

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

    public ArrayList<Restaurant> getAll() {
        return restaurants;
    }
}
