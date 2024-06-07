package Mizdooni.Filter;

import Mizdooni.Security.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

//@Component
@WebFilter(filterName = "JWTFilter", urlPatterns = "/*")
public class JwtFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Handle CORS preflight request
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            httpResponse.setHeader("Access-Control-Allow-Origin", "*"); // Adjust according to your CORS configuration
            httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Skip JWT check for login and signup
        String uri = httpRequest.getRequestURI();
        if (uri.endsWith("/login") || uri.endsWith("/signup")) {
            chain.doFilter(request, response);
            return;
        }

        // Validate JWT token
        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Extract token from header
            String username = JwtUtils.validateToken(token);
            if (username != null) {
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
