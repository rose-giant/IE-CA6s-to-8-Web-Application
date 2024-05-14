package Mizdooni.Controller;


import Mizdooni.Model.User.User;
import Mizdooni.Model.User.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepo;

    @Autowired
    public UserController() throws Exception {
        userRepo = UserRepository.getInstance();
    }

    @GetMapping("")
    public ArrayList<User> getAll() {
        return userRepo.getAll();
    }
//    @PostMapping("/login")
//    public User login(HttpServletResponse response,
//                      @RequestBody Map<String, String> body) throws Exception {
//        UserRepository userRep = UserRepository.getInstance();
//        User user = userRepo.findByUsernameAndPassword(body.get("username"), body.get("password"));
//        if(user != null) {
//            return user;
//        } else {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            throw new Exception("Invalid username or password!");
//        }
//    }
}