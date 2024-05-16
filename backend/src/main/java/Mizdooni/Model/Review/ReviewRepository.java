package Mizdooni.Model.Review;

import Mizdooni.Model.Constants;
import Mizdooni.Model.User.User;

import java.util.ArrayList;

public class ReviewRepository {
    private static ReviewRepository instance;
    private ArrayList<Review> reviews = new ArrayList<>();
    ReviewDAO dao = new ReviewDAO();

    public ReviewRepository() throws Exception {
        dao.createTable("reviews");
        reviews = dao.fetchFromAPI(Constants.GET_REVIEWS_URL, Review.class);
        for (Review review: reviews) {
            dao.addToDatabase(review);
        }
    }

    public ArrayList<Review> findByRestaurant(String restaurantName) {
        ArrayList<Review> results = new ArrayList<>();
        for (Review review: reviews) {
            if (restaurantName.equals(review.restaurantName)) {
                results.add(review);
            }
        }

        return results;
    }

    public ArrayList<Review> findByRestaurantAndUsername(String restaurantName, String username) {
        ArrayList<Review> results = new ArrayList<>();
        for (Review review: reviews) {
            if (restaurantName.equals(review.restaurantName) && username.equals(review.username)) {
                results.add(review);
            }
        }

        return results;
    }

    public static ReviewRepository getInstance() throws Exception {
        if(instance == null)
            return new ReviewRepository();
        else return instance;
    }

    public ArrayList<Review> getAll() {
        return reviews;
    }
}
