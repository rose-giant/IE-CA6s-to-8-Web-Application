package Mizdooni.Controller;

import Mizdooni.Model.Reservation.Reservation;
import Mizdooni.Model.Reservation.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationRepository reservationRepo;
    @Autowired
    public ReservationController() throws Exception {
        reservationRepo = ReservationRepository.getInstance();
    }
    @GetMapping("")
    public ArrayList<Reservation> getAll() throws SQLException {
        return reservationRepo.getAll();
    }
}
