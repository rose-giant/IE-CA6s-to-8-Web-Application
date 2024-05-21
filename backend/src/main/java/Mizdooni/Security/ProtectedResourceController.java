package Mizdooni.Security;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//public class ProtectedResourceController {
//    @GetMapping("/protected")
//    @Secured("ROLE_USER")
//    public String protectedResource() {
//        return "This is a protected resource.";
//    }
//}