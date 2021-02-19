package select.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import select.util.LogCostInterceptor;

/**
 * @author yeyuting
 * @create 2021/1/26
 */
@Configuration
public class InterceptorConfig  implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogCostInterceptor()).addPathPatterns("/**") ;
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
