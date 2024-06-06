package Mizdooni.Filter;
import Mizdooni.Model.User.User;
import Mizdooni.Model.User.UserRepository;
import Mizdooni.Security.JwtUtils;

//import Mizdooni.Security.JwtUtils;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Component
//public class JwtFilter implements Filter {
//    UserRepository userManager = UserRepository.getInstance();
//
//    public JwtFilter() throws Exception {
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//        String url = request.getRequestURI();
//        if(url.equals("/login") || url.equals("/signup")) {
//            chain.doFilter(request, response);
//        }
//        else {
//            String token = request.getHeader("Authorization");
//            if(token == null) {
//                System.out.println("not authorized");
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            }
//            else {
//                String username = JwtUtils.validateToken(token);
//                if(username == null) {
//                    System.out.println("user not found!");
//                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                }
//                else {
//                    System.out.println("user found!");
//                    User user = userManager.findUserByUserName(username);
//                    request.setAttribute("user", user.getUsername());
//                    chain.doFilter(request, response);
//                }
//            }
//        }
//    }
//}
//

//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;



@Component
@WebFilter(filterName = "JWTFilter", urlPatterns = "/*") // Apply this filter to all endpoints
public class JwtFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("JwtFilter initialized");
        // Initialization logic if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("here filter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String uri = httpRequest.getRequestURI();
        // Skip JWT check for login and signup
        if (uri.endsWith("/login") || uri.endsWith("/signup")) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Extract token from header
            String username = JwtUtils.validateToken(token);
            if (username != null) {
                // Token is valid, proceed with the request
                chain.doFilter(request, response);
                return;
            }
        }

        // Token is invalid or missing
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }
}
