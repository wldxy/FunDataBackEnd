package fundata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by ocean on 16-11-23.
 */

@ComponentScan({"fundata.control", "fundata.repository", "fundata.service", "fundata.configure", "fundata.document", "fundata.resolver", "fundata.interceptor", "fundata.advice"})
@SpringBootApplication
@EnableMongoRepositories({"fundata.repository"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
