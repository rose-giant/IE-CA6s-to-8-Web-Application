package Mizdooni.Controller;

import Mizdooni.Model.User.User;
import Mizdooni.Model.User.UserRepository;
import Mizdooni.Model.User.userView;
//import Mizdooni.Security.AuthenticationResponse;
//import Mizdooni.Security.JwtUtil;
import Mizdooni.Security.AuthenticationResponse;
import Mizdooni.Security.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLWarning;
import java.util.Map;

@RestController
@RequestMapping(path= "",produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    public AuthController() throws Exception {
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> login(HttpServletResponse response, @RequestBody Map<String, String> body) throws Exception {
        UserRepository userRepo = UserRepository.getInstance();
        User user = userRepo.findByUsernameAndPassword(body.get("username"), body.get("password"));
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        if (user == null) {
            System.out.println("user is null");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid username or password!");
            response.getWriter().flush();
            System.out.println("Invalid username or password!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            System.out.println(user.username);
            String token = JwtUtil.generateToken(user.getUsername());
            response.setHeader("Authorization", "Bearer " + token);
            response.setStatus(HttpServletResponse.SC_OK);
            System.out.println("Generated token is " + token);

            authenticationResponse.setAccessToken(token);
            return ResponseEntity.ok(authenticationResponse);
        }
    }

    @PostMapping("signup")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody userView userView) throws Exception {
        UserRepository userRepository = UserRepository.getInstance();
        User newUser = userView.viewToUser();
        String token;
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        try {
            token = JwtUtil.generateToken(newUser.username);
            System.out.println("Generated signup token is " + token);
            userRepository.addUser(newUser);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            authenticationResponse.setAccessToken(token);
            return ResponseEntity.ok(authenticationResponse);
//            return new ResponseEntity<>(newUser, headers, HttpStatus.CREATED);
        } catch (SQLWarning e) {
//            return ResponseEntity.badRequest();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            e.printStackTrace();
//            return ResponseEntity.internalServerError();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
