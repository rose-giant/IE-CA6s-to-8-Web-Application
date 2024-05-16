package Mizdooni.Model.Reservation;

import Mizdooni.Model.HibernateUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReservationDAO {
    private static final String TABLE_NAME = "reservation";

    protected void fillInsertValues(PreparedStatement st, Reservation reservation) throws SQLException {
        st.setString(1, reservation.username);
        st.setString(2, reservation.restaurantName);
        st.setString(3, reservation.datetime);
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
        return "INSERT IGNORE INTO %s(reservation_username, reservation_restaurant, datetime) VALUES(?,?,?)";
    }

    public void createTable() throws SQLException {
        Connection con = HibernateUtils.getConnection();
        PreparedStatement createTableStatement = con.prepareStatement(
                String.format(
                        "CREATE TABLE %s (\n" +
                                "    reservation_number BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                                "    reservation_username VARCHAR(255) NOT NULL,\n" +
                                "    reservation_restaurant VARCHAR(255) NOT NULL,\n" +
                                "    table_number INT,\n" +
                                "    datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
                                "    FOREIGN KEY (reservation_username) REFERENCES users (username),\n" +
                                "    FOREIGN KEY (reservation_restaurant) REFERENCES restaurants (name)\n" +
                                ");\n",
                        TABLE_NAME)
        );
        System.out.println(createTableStatement);
        createTableStatement.executeUpdate();
        createTableStatement.close();
        con.close();
    }
}
