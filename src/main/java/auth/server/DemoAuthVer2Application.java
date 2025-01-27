package auth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "auth.server")
public class DemoAuthVer2Application {
    public static void main(String[] args) {
        SpringApplication.run(DemoAuthVer2Application.class, args);
    }
}
