package Mizdooni.Filter;
import Mizdooni.Model.User.User;
import Mizdooni.Model.User.UserRepository;

import Mizdooni.Security.JwtUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter implements Filter {
    UserRepository userManager = UserRepository.getInstance();

    public JwtFilter() throws Exception {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String url = request.getRequestURI();
        if(url.equals("/login") || url.equals("/signup")) {
            chain.doFilter(request, response);
        }
        else {
            String token = request.getHeader("Authorization");
            if(token == null) {
                System.out.println("not authorized");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            else {
                String username = JwtUtils.verifyJWT(token);
                if(username == null) {
                    System.out.println("user not found!");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                }
                else {
                    System.out.println("user found!");
                    User user = userManager.findUserByUserName(username);
                    request.setAttribute("user", user.getUsername());
                    chain.doFilter(request, response);
                }
            }
        }
    }
}

