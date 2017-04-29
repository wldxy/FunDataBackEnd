package fundata;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.jms.Queue;

/**
 * Created by ocean on 16-11-23.
 */

@ComponentScan({"fundata.control", "fundata.repository", "fundata.service", "fundata.configure", "fundata.document", "fundata.resolver", "fundata.interceptor", "fundata.advice", "fundata.message"})
@SpringBootApplication
@EnableMongoRepositories({"fundata.repository"})
public class Application {
    @Bean
    public Queue queue() {
        return new ActiveMQQueue("sample.queue");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
