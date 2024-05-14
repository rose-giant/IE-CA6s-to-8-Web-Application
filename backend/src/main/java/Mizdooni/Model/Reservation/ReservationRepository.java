package Mizdooni.Model.Reservation;

import java.util.ArrayList;

public class ReservationRepository {
    private static ReservationRepository instance;
    private ArrayList<Reservation> restaurants = new ArrayList<>();

    public ReservationRepository() throws Exception {
        ReservationDAO dao = new ReservationDAO();
        restaurants = dao.getFromAPI();
    }

    public static ReservationRepository getInstance() throws Exception {
        if(instance == null)
            return new ReservationRepository();
        else return instance;
    }

    public ArrayList<Reservation> getAll() {
        return restaurants;
    }
}
