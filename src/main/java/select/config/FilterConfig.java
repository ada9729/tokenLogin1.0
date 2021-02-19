package select.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import select.util.LogCostFilter;

/**
 * @author yeyuting
 * @create 2021/1/26
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean registerFilter(){
        FilterRegistrationBean registration = new FilterRegistrationBean() ;
        registration.setFilter(new LogCostFilter());
        registration.addUrlPatterns("/*");
        registration.setName("logCostFilter");
        registration.setOrder(1);
        return registration;
    }
}
