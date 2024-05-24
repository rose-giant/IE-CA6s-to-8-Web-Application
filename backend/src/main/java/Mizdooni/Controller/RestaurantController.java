package Mizdooni.Controller;

import Mizdooni.Model.Address;
import Mizdooni.Model.Restaurant.Restaurant;
import Mizdooni.Model.Restaurant.RestaurantRepository;
import Mizdooni.Model.User.User;
import Mizdooni.Model.User.UserRepository;
import Mizdooni.Model.User.userView;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantRepository restaurantRepo;
    @Autowired
    public RestaurantController() throws Exception {
        restaurantRepo = RestaurantRepository.getInstance();
    }
    @GetMapping("")
    public ArrayList<Restaurant> getAll(
            @RequestBody (required = false)Map<String, String> body
    ) throws SQLException {

        return restaurantRepo.getAll(null, null, null);

    }
    @GetMapping("/search")
    public ArrayList<Restaurant> getSearch(
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type
    ) throws SQLException {
        return restaurantRepo.getAll(address, name, type);
    }
    @GetMapping("/{username}")
    public ArrayList<Restaurant> getRestaurantsOfUser(@PathVariable String username) throws SQLException {
        return restaurantRepo.findRestaurantsByManager(username);
    }

    @GetMapping("/top/{number}")
    public ArrayList<Restaurant> getTopTenRestaurants(@PathVariable int number) throws SQLException {
        return restaurantRepo.findTopRestaurants(number);
    }

    @PostMapping("")
    public Restaurant addRestaurant(HttpServletResponse response,
                              @RequestBody Map<String, String> body) throws Exception {
        RestaurantRepository restRepository = RestaurantRepository.getInstance();
        Address ad = new Address(body.get("city"), body.get("country"),body.get("street"));
        Restaurant newRest = new Restaurant(ad, body.get("description"), body.get("endTime"),body.get("image"),body.get("managerUsername"),body.get("name"),body.get("startTime"), body.get("type") );
        try{
            restRepository.addRestaurant(newRest);
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
