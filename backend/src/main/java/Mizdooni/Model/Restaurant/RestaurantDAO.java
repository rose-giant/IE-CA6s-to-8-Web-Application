package Mizdooni.Model.Restaurant;

import Mizdooni.Model.Address;
import Mizdooni.Model.Constants;
import Mizdooni.Model.DAO;
import Mizdooni.Model.HibernateUtils;
import Mizdooni.Model.Review.Review;
import Mizdooni.Model.User.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static Mizdooni.Model.Constants.RESTAURANTS_TABLE_NAME;

public class RestaurantDAO extends DAO<Restaurant> {
    public ArrayList<Restaurant> getFromAPI() throws Exception{
        String RestaurantsJsonString = getRequest(Constants.GET_RESTAURANTS_URL);
        ObjectMapper om = new ObjectMapper();
        return om.readValue(RestaurantsJsonString, new TypeReference<ArrayList<Restaurant>>(){});
    }

    public String getCreateTableQuery(String restaurantsTableName) {
        return String.format(
                "CREATE TABLE IF NOT EXISTS %s " +
                        "(name CHAR(225),\nmanagerUsername CHAR(225),\ntype CHAR(225),startTime CHAR(225)," +
                        "\nendTime CHAR(225),\ndescription TEXT,\nimage CHAR(225),\naddress CHAR(225)," +
                        "\nPRIMARY KEY(name), \nFOREIGN KEY(managerUsername)REFERENCES manager(username));",
                restaurantsTableName);
    }

    @Override
    protected Restaurant convertToDomainModel(ResultSet rs) {
        try{
            Address ad = new Address().toAddress(rs.getString(8));
            return new Restaurant(ad, rs.getString(6), rs.getString(5), rs.getString(7), rs.getString(2), rs.getString(1), rs.getString(4), rs.getString(3));
        }
        catch (Exception e){
            System.out.println("convertToDomainModelError: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected String getAllQuery() {
        return "SELECT * FROM " + RESTAURANTS_TABLE_NAME;
    }

    @Override
    protected void fillInsertValues(PreparedStatement st, Restaurant rest) throws SQLException {
        st.setString(1, rest.getName());
        st.setString(2, rest.getManagerUsername());
        st.setString(3, rest.getType());
        st.setString(4, rest.getStartTime());
        st.setString(5, rest.getEndTime());
        st.setString(6, rest.getDescription());
        st.setString(7, rest.getImage());
        st.setString(8, rest.getAddress().toString());
    }

    @Override
    protected String getInsertRecordQuery() {
        return String.format("INSERT IGNORE INTO %s(name,managerUsername,type,startTime,endTime,description,image,address) VALUES(?,?,?,?,?,?,?,?)", RESTAURANTS_TABLE_NAME );
    }


    protected User convertToDomainModel(ResultSet res, String tableName) {
        return null;
    }

    public ArrayList<Restaurant> findTopRate(int number) {
//        String query = "SELECT * FROM " + RESTAURANTS_TABLE_NAME + " t WHERE t.";
        return null;
    }
}
