package Mizdooni.Model.Reservation;

import Mizdooni.Model.Address;
import Mizdooni.Model.Constants;
import Mizdooni.Model.DAO;
import Mizdooni.Model.Restaurant.Restaurant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import Mizdooni.Model.HibernateUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Mizdooni.Model.Constants.RESERVES_TABLE_NAME;
import static Mizdooni.Model.Constants.REVIEWS_TABLE_NAME;

public class ReservationDAO extends DAO<Reservation> {

    public ArrayList<Reservation> getFromAPI() throws Exception{
        String ReservationsJsonString = "[{\"username\": \"Mostafa_Ebrahimi\", \"restaurantName\": \"Sullivan's Steakhouse\", \"tableNumber\": 1, \"datetime\": \"1986-04-08 12:30\"}, {\"username\": \"Arshia_Abolghasemi\", \"restaurantName\": \"Sullivan's Steakhouse\", \"tableNumber\": 1, \"datetime\": \"1986-04-09 12:30\"},{\"username\": \"MohammadSadegh_Aboofazeli\", \"restaurantName\": \"Sullivan's Steakhouse\", \"tableNumber\": 1, \"datetime\": \"2000-04-08 12:30\"},{\"username\": \"Mostafa_Ebrahimi\", \"restaurantName\": \"Sullivan's Steakhouse\", \"tableNumber\": 4, \"datetime\": \"1986-04-08 12:30\"}, {\"username\": \"Arshia_Abolghasemi\", \"restaurantName\": \"Sullivan's Steakhouse\", \"tableNumber\": 3, \"datetime\": \"1986-04-09 12:30\"},{\"username\": \"MohammadSadegh_Aboofazeli\", \"restaurantName\": \"Sullivan's Steakhouse\", \"tableNumber\": 3, \"datetime\": \"2000-04-08 12:30\"}]";
        ObjectMapper om = new ObjectMapper();
        return om.readValue(ReservationsJsonString, new TypeReference<ArrayList<Reservation>>(){});
    }

    @Override
    protected String getCreateTableQuery(String tableName) {
        return String.format(
                "CREATE TABLE %s (\n" +
                        "    reservation_number BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "    reservation_username CHAR(225) NOT NULL,\n" +
                        "    reservation_restaurant CHAR(225) NOT NULL,\n" +
                        "    datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
                        "    tableNumber INT,\n" +
                        "    FOREIGN KEY (reservation_username) REFERENCES client (username),\n" +
                        "    FOREIGN KEY (reservation_restaurant) REFERENCES restaurant (name),\n" +
                        "    FOREIGN KEY (tableNumber) REFERENCES rest_table (id)\n"+
                        ");\n",
                tableName);
    }

    @Override
    protected Reservation convertToDomainModel(ResultSet rs) {
        try{
            System.out.println(rs);
            return new Reservation(rs.getString(2), rs.getString(3), rs.getInt(5), rs.getString(4));
        }
        catch (Exception e){
            System.out.println("convertToDomainModelError: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected String getAllQuery() {
        return "SELECT * FROM " + RESERVES_TABLE_NAME + ";";
    }

    private static final String TABLE_NAME = "reservation";

    protected void fillInsertValues(PreparedStatement st, Reservation reservation) throws SQLException {
        st.setString(1, reservation.username);
        st.setString(2, reservation.restaurantName);
        st.setString(3, reservation.datetime);
        st.setInt(4, reservation.tableNumber);
    }
    @Override
    protected String getInsertRecordQuery() {
        return String.format("INSERT IGNORE INTO %s(reservation_username, reservation_restaurant, datetime, tableNumber) VALUES(?,?,?,?)", Constants.RESERVES_TABLE_NAME) ;
    }


}
