package app.infrastructure.config;

import app.infrastructure.controller.interceptor.SafetyNetAlertInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getSafetyNetAlertInterceptor());
    }

    @Bean
    public SafetyNetAlertInterceptor getSafetyNetAlertInterceptor() {
        return new SafetyNetAlertInterceptor();
    }
}