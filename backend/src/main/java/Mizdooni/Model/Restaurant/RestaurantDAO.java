package Mizdooni.Model.Restaurant;

import Mizdooni.Model.Constants;
import Mizdooni.Model.DAO;
import Mizdooni.Model.HibernateUtils;
import Mizdooni.Model.User.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import static Mizdooni.Model.Constants.RESTAURANTS_TABLE_NAME;

public class RestaurantDAO extends DAO {
    public ArrayList<Restaurant> getFromAPI() throws Exception{
        String RestaurantsJsonString = getRequest(Constants.GET_RESTAURANTS_URL);
        ObjectMapper om = new ObjectMapper();
        return om.readValue(RestaurantsJsonString, new TypeReference<ArrayList<Restaurant>>(){});
    }

    public void createTable(String restaurantsTableName) throws SQLException {
        Connection con = HibernateUtils.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s " +
                                "(name CHAR(225),\nmanagerUsername CHAR(225),\ntype CHAR(225),startTime CHAR(225)," +
                                "\nendTime CHAR(225),\ndescription CHAR(225),\nimage CHAR(225),\naddress CHAR(225)," +
                                "\nPRIMARY KEY(name), \nFOREIGN KEY(managerUsername)REFERENCES manager(username));",
                        restaurantsTableName)
        );
        System.out.println(createTableStatement);
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }

    public void addToDatabase(Restaurant rest) throws SQLException {
        Connection con = HibernateUtils.getConnection();
        String insertQuery = getInsertRecordQuery();
        PreparedStatement st = con.prepareStatement(insertQuery);
        fillInsertValues(st, rest);
        System.out.println(st);
        try {
            st.execute();
            st.close();
            con.close();
        } catch (Exception e) {
            st.close();
            con.close();
            System.out.println("error in Repository.insert query.");
            e.printStackTrace();
        }
    }

    private void fillInsertValues(PreparedStatement st, Restaurant rest) throws SQLException {
        st.setString(1, rest.getName());
        st.setString(2, rest.getManagerUsername());
        st.setString(3, rest.getType());
        st.setString(4, rest.getStartTime());
        st.setString(5, rest.getEndTime());
        st.setString(6, rest.getDescription());
        st.setString(7, rest.getImage());
        st.setString(8, rest.getAddress().toString());
    }

    private String getInsertRecordQuery() {
        return String.format("INSERT IGNORE INTO %s(name,managerUsername,type,startTime,endTime,description,image,address) VALUES(?,?,?,?,?,?,?,?)", RESTAURANTS_TABLE_NAME );
    }
}
