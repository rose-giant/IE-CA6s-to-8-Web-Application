package Mizdooni.Model.Reservation;

import Mizdooni.Model.Constants;
import Mizdooni.Model.DAO;
import Mizdooni.Model.Reservation.Reservation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.util.ArrayList;
import Mizdooni.Model.HibernateUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReservationDAO extends DAO {

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
                        "    reservation_username VARCHAR(255) NOT NULL,\n" +
                        "    reservation_restaurant VARCHAR(255) NOT NULL,\n" +
                        "    datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
                        "    tableNumber INT,\n" +
                        "    FOREIGN KEY (reservation_username) REFERENCES client (username),\n" +
                        "    FOREIGN KEY (reservation_restaurant) REFERENCES restaurant (name),\n" +
                        "    FOREIGN KEY (tableNumber) REFERENCES rest_table (tableNumber)\n"+
                        ");\n",
                tableName);
    }

    private static final String TABLE_NAME = "reservation";

    protected void fillInsertValues(PreparedStatement st, Reservation reservation) throws SQLException {
        st.setString(1, reservation.username);
        st.setString(2, reservation.restaurantName);
        st.setString(3, reservation.datetime);
        st.setInt(4, reservation.tableNumber);
    }

    public void addToDatabase(Reservation reservation) throws SQLException {
        Connection con = HibernateUtils.getConnection();
        String insertQuery = getInsertRecordQuery();
        PreparedStatement st = con.prepareStatement(insertQuery);
        fillInsertValues(st, reservation);
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

    private String getInsertRecordQuery() {
        return String.format("INSERT IGNORE INTO %s(reservation_username, reservation_restaurant, datetime, tableNumber) VALUES(?,?,?,?)", Constants.RESERVES_TABLE_NAME) ;
    }


}
