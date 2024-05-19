package Mizdooni.Controller;

import Mizdooni.Model.Reservation.Reservation;
import Mizdooni.Model.Reservation.ReservationRepository;
import Mizdooni.Model.Reservation.Reservation;
import Mizdooni.Model.Reservation.ReservationRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationRepository reservationRepo;
    @Autowired
    public ReservationController() throws Exception {
        reservationRepo = ReservationRepository.getInstance();
    }
    @GetMapping("")
    public ArrayList<Reservation> getReservation(
            @RequestBody (required = false)Map<String, String> body
    ) throws SQLException {
        if(body == null) return reservationRepo.getAll(null, null, null);
        return reservationRepo.getAll(body.get("username"), body.get("restName"), body.get("tableNum"));
    }

    @PostMapping("")
    public Reservation addReservation(HttpServletResponse response,
                           @RequestBody Map<String, String> body) throws Exception {
        ReservationRepository reservationRepository = ReservationRepository.getInstance();
        Reservation newRest = new Reservation( body.get("username"),body.get("restaurantName")
                ,Integer.parseInt(body.get("tableNumber")), body.get("dateTime"));
        try{
            reservationRepository.addReservation(newRest);
            response.setStatus(HttpServletResponse.SC_CREATED);
            return newRest;
        }catch (SQLWarning e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
            response.getWriter().flush();
            System.out.println(e.getMessage());
        }
        return null;
    }
}
