package Mizdooni;


import Mizdooni.Model.Reservation.ReservationRepository;
import Mizdooni.Model.Restaurant.Restaurant;
import Mizdooni.Model.Restaurant.RestaurantRepository;
import Mizdooni.Model.Review.ReviewRepository;
import Mizdooni.Model.Table.TableRepository;
import Mizdooni.Model.User.UserRepository;
import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.Scope;
import co.elastic.apm.api.Span;
import co.elastic.apm.api.Transaction;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.ArrayList;

@SpringBootApplication
public class MizdooniApplication extends SpringBootServletInitializer {
	public static void main(String[] args) throws Exception {

		Transaction transaction = ElasticApm.startTransaction();
		try (Scope txScope = transaction.activate()) {
			transaction.setName("FetchFromDateBase");
			transaction.setType(Transaction.TYPE_REQUEST);

			Span getRestsSpan = transaction.startSpan("getAllRests", "query", "findRestaurants");
			try (Scope dbScope = getRestsSpan.activate()) {
				getRestsSpan.setName("fetch rom database");

//                LOGGER.info("get all restaurants API requested");
				RestaurantRepository.getInstance();
				getRestsSpan.end();


			} catch (Exception e) {
				getRestsSpan.captureException(e);
				getRestsSpan.end();
				throw e;
			}
		} catch (Exception e) {
			transaction.captureException(e);
		} finally {
			transaction.end();
		}

		UserRepository.getInstance();
//		RestaurantRepository.getInstance();
		TableRepository.getInstance();
		ReservationRepository.getInstance();
		ReviewRepository.getInstance();

		SpringApplication.run(MizdooniApplication.class, args);
	}
}

