package Mizdooni.Model.Reservation;

import Mizdooni.Model.Constants;
import Mizdooni.Model.HibernateUtils;
import Mizdooni.Model.Table.TableRest;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static Mizdooni.Model.Constants.*;

public class ReservationRepository {
    private static ReservationRepository instance;
    private ArrayList<Reservation> reservations = new ArrayList<>();
    ReservationDAO dao = new ReservationDAO();

    public ReservationRepository() throws Exception {
        if(!dao.checkTableExistence(RESERVES_TABLE_NAME)){
            reservations = dao.getFromAPI();
            for (Reservation user:reservations) {
                dao.addToDatabase(user);
            }
        }
    }

    public static ReservationRepository getInstance() throws Exception {
        if(instance == null)
            return new ReservationRepository();
        else return instance;
    }

    public ArrayList<Reservation> getAll(String userName, String restName, String tableNum) throws SQLException {
        ArrayList fields = new ArrayList<>();
        ArrayList values = new ArrayList<>();
        if(userName != null){
            values.add(userName);
            fields.add("reservation_username");
        }if(restName != null){
            values.add(restName);
            fields.add("reservation_restaurant");
        }if(tableNum != null){
            values.add(tableNum);
            fields.add("tableNumber");
        }
        System.out.println("####" + values);
        System.out.println("####" + fields);
        System.out.println("findByFields begin");
        Connection conn = HibernateUtils.getConnection();
        System.out.println("after getting conn");
        if(values.size() == 0) return dao.getAll();
        else return dao.findByFields(conn, values, fields, RESERVES_TABLE_NAME);
    }

    public void addReservation(Reservation newRest) throws SQLException {
        dao.addToDatabase(newRest);
    }

    public ArrayList<Reservation> findReservationsByUsername(String username) throws SQLException {
        System.out.println("findByFields begin");
        Connection conn = HibernateUtils.getConnection();
        System.out.println("after getting conn");
        return dao.findByFields(conn, Arrays.asList(username),
                Arrays.asList("reservation_username"), RESERVES_TABLE_NAME);
    }

    public ArrayList<Reservation> findReservationsByRestaurantName(String restName) throws SQLException {
        System.out.println("findByFields begin");
        Connection conn = HibernateUtils.getConnection();
        System.out.println("after getting conn");
        return dao.findByFields(conn, Arrays.asList(restName),
                Arrays.asList("reservation_restaurant"), RESERVES_TABLE_NAME);
    }

    public ArrayList<Reservation> findReservationsByRestaurantNameAndTableNumber(String restName, String tableNumber) throws SQLException {
        System.out.println("findByFields begin");
        Connection conn = HibernateUtils.getConnection();
        System.out.println("after getting conn");
        return dao.findByFields(conn, Arrays.asList(restName, tableNumber),
                Arrays.asList("reservation_restaurant", "tableNumber"), RESERVES_TABLE_NAME);
    }
}
