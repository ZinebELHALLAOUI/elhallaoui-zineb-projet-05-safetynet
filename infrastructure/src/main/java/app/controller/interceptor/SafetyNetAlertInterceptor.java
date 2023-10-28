package app.controller.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SafetyNetAlertInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(SafetyNetAlertInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String url = request.getRequestURI();
        String method = request.getMethod();

        logger.info("Input request : " + method + " " + url);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        String url = request.getRequestURI();
        String method = request.getMethod();
        logger.info("Output status of " + method + " " + url + " : " + response.getStatus());
    }

}