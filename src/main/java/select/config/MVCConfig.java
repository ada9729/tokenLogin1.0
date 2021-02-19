package select.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import select.util.AuthenticationInterceptor;

/**
 * @author yeyuting
 * @create 2021/1/26
 */
@Configuration
public class MVCConfig implements WebMvcConfigurer {
    @Bean
    public HandlerInterceptor authenticationInterceptor(){
        return new AuthenticationInterceptor() ;
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/") ;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/sys/user/*")
                .excludePathPatterns("/sys/user/loginCheck")
                .excludePathPatterns("/sys/user/login") ;
    }
}
