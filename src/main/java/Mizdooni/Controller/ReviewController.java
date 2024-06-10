package Mizdooni.Controller;


import Mizdooni.Model.Review.Review;
import Mizdooni.Model.Review.ReviewRepository;
import Mizdooni.Model.Table.TableRepository;
import Mizdooni.Model.Table.TableRest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.Map;


@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewRepository reviewRepo;
    @Autowired
    public ReviewController() throws Exception {
        reviewRepo = ReviewRepository.getInstance();
    }
    @GetMapping("")
    public ArrayList<Review> getAll() throws SQLException {
        return reviewRepo.getAll();
    }
    @GetMapping("/{RestaurantName}")
    public ArrayList<Review> getReviewsOfRestaurant(@PathVariable String RestaurantName) throws SQLException {
        return reviewRepo.findByRestaurant(RestaurantName);
    }
    @PostMapping("")
    public Review addTable(HttpServletResponse response,
                              @RequestBody Map<String, String> body) throws Exception {
        ReviewRepository reviewRepository = ReviewRepository.getInstance();
        Review newRest = new Review(Double.parseDouble(body.get("ambianceRate")), body.get("comment"),
                Double.parseDouble(body.get("foodRate")),Double.parseDouble(body.get("overallRate")),
                body.get("restaurantName"),Double.parseDouble(body.get("serviceRate")), body.get("username"));
        try{
            reviewRepository.addReview(newRest);
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
