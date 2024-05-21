package Mizdooni.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CorsFilter;

//@Configuration
//public class FilterConfig {
//
//    @Bean
//    public FilterRegistrationBean<CorsFilter> corsFilter() {
//        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
////        registrationBean.setFilter(new CorsFilter());
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setOrder(0); // Ensure this filter runs first
//        return registrationBean;
//    }

//    @Bean
//    public FilterRegistrationBean<JwtFilter> jwtFilter() {
//        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new JwtFilter());
//        registrationBean.addUrlPatterns("/protected/*");
//        registrationBean.setOrder(1); // Ensure this filter runs after the CORS filter
//        return registrationBean;
//    }
//}
