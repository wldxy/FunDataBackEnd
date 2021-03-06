package fundata;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.Queue;

/**
 * Created by ocean on 16-11-23.
 */

@ComponentScan({"fundata.control", "fundata.repository", "fundata.service", "fundata.configure", "fundata.document", "fundata.resolver", "fundata.interceptor", "fundata.advice", "fundata.message"})
@SpringBootApplication
@EnableMongoRepositories({"fundata.repository"})
public class Application {
    @Bean(name = "pullrequest")
    public Queue pullRequestQueue() {
        return new ActiveMQQueue("pullrequest.queue");
    }

    @Bean(name = "mergerequest")
    public Queue mergeRequestQueue() {
        return new ActiveMQQueue("mergerequest.queue");
    }

    @Bean(name = "mergeresult")
    public Queue mergeResultQueue() {
        return new ActiveMQQueue("mergeresult.queue");
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
