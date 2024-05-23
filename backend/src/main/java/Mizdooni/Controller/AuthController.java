package Mizdooni.Controller;

import Mizdooni.Model.User.User;
import Mizdooni.Model.User.UserRepository;
import Mizdooni.Model.User.userView;
import Mizdooni.Security.ApplicationConfig;
import Mizdooni.Security.AuthenticationRequest;
import Mizdooni.Security.AuthenticationResponse;
import Mizdooni.Security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.http.HttpHeaders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLWarning;
import java.util.Collection;

@RestController
@RequestMapping(path= "",produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    public AuthController() throws Exception {
    }
    private final AuthenticationManager authenticationManager = new AuthenticationManager() {
        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            return null;
        }
    };

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        UserRepository userRepo = UserRepository.getInstance();

        System.out.println("request is " + authenticationRequest.getUsername() + authenticationRequest.getPassword());
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(), authenticationRequest.getPassword()
                )
        );

        System.out.println("request is " + authenticationRequest.getUsername() + authenticationRequest.getPassword());
//        User user = userRepo.findUserByUserName(authenticationRequest.getUsername());
        User user = userRepo.findByUsernameAndPassword(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        System.out.println("logged in user is "+ user.username + ", " + user.password);
        String token = JwtUtil.generateToken(user);
        System.out.println("Generated token is " + token);
        authenticationResponse.setAccessToken(token);
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.ok(authenticationResponse);
    }

    private final ApplicationConfig applicationConfig = new ApplicationConfig();

    @PostMapping("signup")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody userView userView) throws Exception {
        System.out.println("yeahhhhhhhhhhhh");
        UserRepository userRepository = UserRepository.getInstance();
        User newUser = userView.viewToUser();
        String token;
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        try {
            token = JwtUtil.generateToken(newUser);
            System.out.println("Generated signup token is " + token);

            newUser.password = applicationConfig.hmac(newUser.password);
            System.out.println("hashed password is " + newUser.password);

            userRepository.addUser(newUser);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            authenticationResponse.setAccessToken(token);
            return ResponseEntity.ok(authenticationResponse);
        } catch (SQLWarning e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
            ) {
        return null;
    }

    @PostMapping("/callback")
    public ResponseEntity<AuthenticationResponse> callbcak(
            @RequestBody AuthenticationRequest request
    ) {
        return null;
    }

//    @GetMapping("oauth")
//    public String oauth(Model model , @AuthenticationPrincipal OAuth2User user) throws Exception {
//        return "index";
//    }
    @GetMapping("/redirect")
    public void oauthToken(@RequestParam String token) {
        System.out.println(token);
    }
}
















