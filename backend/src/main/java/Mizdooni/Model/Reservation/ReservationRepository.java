package Mizdooni.Model.Reservation;

import Mizdooni.Model.Constants;
import Mizdooni.Model.Table.TableRest;

import java.sql.SQLException;
import java.util.ArrayList;

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
}
