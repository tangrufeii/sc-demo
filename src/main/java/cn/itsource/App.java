package cn.itsource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author tangrufei
 * @date 2022-09-07 11:15
 */
@SpringBootApplication
public class App {
      public static void main(String[] args) {
              SpringApplication.run(App.class,args);
      }
}
