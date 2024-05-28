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
//        if(!dao.checkTableExistence(RESTAURANTS_TABLE_NAME)){
//            restaurants = dao.fetchFromAPI(Constants.GET_RESTAURANTS_URL, Restaurant.class);
//            for (Restaurant rest:restaurants) {
//                dao.addToDatabase(rest);
//            }
//        }
//        restaurants = dao.fetchFromAPI(Constants.GET_RESTAURANTS_URL, Restaurant.class);
        restaurants = dao.getAll();
    }

    public static RestaurantRepository getInstance() throws Exception {
        if(instance == null)
            instance = new RestaurantRepository();
        return instance;
    }

    public boolean isEmpty(String in){
        if(in == null || in.trim().isEmpty()) return true;
        else return false;
    }
    public ArrayList<Restaurant> getAll(String location, String name, String type) throws SQLException {
//        ArrayList fields = new ArrayList<>();
//        ArrayList values = new ArrayList<>();
//
        ArrayList<Restaurant> rs = new ArrayList<>();
        System.out.println(location+ "-  "+ name + "- " + type);

        for (Restaurant rest: restaurants) {
            if ( (isEmpty(location) || rest.address.country.equals(location)) &&
                    (isEmpty(type) || rest.type.equals(type)) &&
                    (isEmpty(name) || rest.name.equals(name)) ) {
                rs.add(rest);
            }
        }
        return rs;

//        if(location != null && location != ""){
//            rs = findRestaurantsByLocation(restaurants, location);
//        }if(name != null && name != ""){
//            if(rs.size() == 0) rs = findRestaurantsByName(restaurants, name);
//            else rs = findRestaurantsByName(rs, name);
//        }if(type != null && type != ""){
//            if(rs.size() == 0) rs = findRestaurantsByName(restaurants, name);
//            else rs = findRestaurantsByName(rs, name);
//        }
//        return rs;
//        System.out.println("restaurant values; ####" + values);
//        System.out.println("restaurant fields;####" + fields);
//
//        System.out.println("findByFields begin");
//        Connection conn = HibernateUtils.getConnection();
//        System.out.println("after getting conn");
//
//        if(values.size() == 0) return dao.getAll();
//        else return dao.findByFields(conn, values, fields, RESTAURANTS_TABLE_NAME);

    }

    public void addRestaurant(Restaurant newRest) throws SQLException {
//        dao.addToDatabase(newRest);
        restaurants.add(newRest);
    }

    public ArrayList<Restaurant> findRestaurantsByManager(String username) throws SQLException {
//        System.out.println("findByFields begin");
//        Connection conn = HibernateUtils.getConnection();
//        System.out.println("after getting conn");
//        return dao.findByFields(conn, Arrays.asList(username),
//                Arrays.asList("managerUsername"), RESTAURANTS_TABLE_NAME);
        ArrayList<Restaurant> rs = new ArrayList<>();
        for (Restaurant rest: restaurants) {
            if (username.equals(rest.managerUsername)) {
                rs.add(rest);
            }
        }
        return rs;
    }

    public ArrayList<Restaurant> findRestaurantsByLocation( String location) throws SQLException {
        ArrayList<Restaurant> rs = new ArrayList<>();
        for (Restaurant rest: restaurants) {
            if (location.equals(rest.address.country)) {
                rs.add(rest);
            }
        }
        return rs;
    }

    public ArrayList<Restaurant> findRestaurantsByName(String location) throws SQLException {
        ArrayList<Restaurant> rs = new ArrayList<>();
        for (Restaurant rest: restaurants) {
            if (location.equals(rest.name)) {
                rs.add(rest);
            }
        }
        return rs;
    }

    public ArrayList<Restaurant> findRestaurantsByType(String location) throws SQLException {
        ArrayList<Restaurant> rs = new ArrayList<>();
        for (Restaurant rest: restaurants) {
            if (location.equals(rest.type)) {
                rs.add(rest);
            }
        }
        return rs;
    }

    public ArrayList<Restaurant> findTopRestaurants(int number) {
        return dao.findTopRate(number);
    }
}
