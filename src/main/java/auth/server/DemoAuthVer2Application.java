package auth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication(scanBasePackages = "auth.server")
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class DemoAuthVer2Application {
    public static void main(String[] args) {
        SpringApplication.run(DemoAuthVer2Application.class, args);
    }
}
