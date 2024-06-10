package Mizdooni;

import Mizdooni.Model.Reservation.ReservationRepository;
import Mizdooni.Model.Restaurant.RestaurantRepository;
import Mizdooni.Model.Review.ReviewRepository;
import Mizdooni.Model.Table.TableRepository;
import Mizdooni.Model.User.UserRepository;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MizdooniApplication extends SpringBootServletInitializer {
	public static void main(String[] args) throws Exception {
		UserRepository.getInstance();
		RestaurantRepository.getInstance();
		TableRepository.getInstance();
		ReservationRepository.getInstance();
		ReviewRepository.getInstance();

		SpringApplication.run(MizdooniApplication.class, args);
	}
}