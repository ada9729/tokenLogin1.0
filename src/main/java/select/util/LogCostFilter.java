package select.util;


import javax.servlet.*;
import java.io.IOException;

/**
 * @author yeyuting
 * @create 2021/1/26
 */
public class LogCostFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long start = System.currentTimeMillis() ;
        chain.doFilter(request,response);
        System.out.println("执行时间为："+ (System.currentTimeMillis()-start));
    }

    @Override
    public void destroy() {

    }
}
