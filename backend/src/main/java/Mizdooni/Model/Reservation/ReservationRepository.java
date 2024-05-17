package Mizdooni.Model.Reservation;

import Mizdooni.Model.Constants;
import Mizdooni.Model.Table.TableRest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static Mizdooni.Model.Constants.RESERVES_TABLE_NAME;
import static Mizdooni.Model.Constants.TABLES_TABLE_NAME;

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

    public ArrayList<Reservation> getAll() throws SQLException {
        return dao.getAll();
    }

    public void addReservation(Reservation newRest) throws SQLException {
        dao.addToDatabase(newRest);
    }

    public ArrayList<Reservation> findReservationsByUsername(String username) throws SQLException {
        return dao.findByFields(Arrays.asList(username),
                Arrays.asList("reservation_username"), RESERVES_TABLE_NAME);
    }

    public ArrayList<Reservation> findReservationsByRestaurantName(String restName) throws SQLException {
        return dao.findByFields(Arrays.asList(restName),
                Arrays.asList("reservation_restaurant"), RESERVES_TABLE_NAME);
    }

    public ArrayList<Reservation> findReservationsByRestaurantNameAndTableNumber(String restName, String tableNumber) throws SQLException {
        return dao.findByFields(Arrays.asList(restName, tableNumber),
                Arrays.asList("reservation_restaurant", "tableNumber"), RESERVES_TABLE_NAME);
    }
}
