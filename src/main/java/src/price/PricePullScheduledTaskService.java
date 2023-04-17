package src.price;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.jms.JMSException;
import javax.jms.Topic;
import java.util.Objects;

@Component
@AllArgsConstructor
@Slf4j
public class PricePullScheduledTaskService {

    private final String priceUrl = "http://127.0.0.1:5000/btc-price";

    @Autowired
    private final JmsTemplate jmsTemplate;

    @Autowired
    private final RestTemplate restTemplate;


    @Scheduled(fixedRate = 5000)
    public void execute() throws JMSException {
        log.info("**********  Executing scheduled task: PricePullScheduledTask ************");
        PriceInfo response;

        Topic priceTopic = Objects.requireNonNull(jmsTemplate.getConnectionFactory()).createConnection()
                .createSession().createTopic("PriceTopic");
        try {
            response = restTemplate.getForObject(priceUrl, PriceInfo.class);
            log.info(String.format("Producer sending new price: %f %s", response.getPrice(), response.getTimestamp()));
            jmsTemplate.convertAndSend(priceTopic, response);
        } catch (Exception e) {
            log.error("Error occurred when fetching price data: ", e);
        }
    }
}
