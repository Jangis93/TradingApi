package src;

import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.activemq.artemis.core.server.ActiveMQServers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class Application {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    public static void main(String[] args) throws Exception {

        // Embedded Server Configuration
        ActiveMQServer activeMQServer
                = ActiveMQServers.newActiveMQServer(
                new ConfigurationImpl()
                        .setPersistenceEnabled(false)
                        .setJournalDirectory(
                                "target/data/journal")
                        .setSecurityEnabled(false)
                        .addAcceptorConfiguration("invm",
                                "vm:///localhost:61616"));

        activeMQServer.start();

       SpringApplication.run(Application.class, args);
    }
}
