package taras.korolchuk.filecompressor.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // This allows CORS requests to any URL
                        .allowedOrigins("http://localhost:63342") // Specify the allowed origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Specify the allowed HTTP methods
                        .allowedHeaders("*") // Allow all headers
                        .exposedHeaders(HttpHeaders.CONTENT_DISPOSITION)
                        .allowCredentials(true);
            }
        };
    }
}

