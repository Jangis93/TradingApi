package src.price;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PricePullScheduledTaskService {

    private final Logger logger = LoggerFactory.getLogger(PricePullScheduledTaskService.class);

    private final RestTemplate restTemplate;
    private final String priceUrl = "http://127.0.0.1:5000/btc-price";

    public PricePullScheduledTaskService() {
        this.restTemplate = new RestTemplate(); // create bean?
    }

    @Scheduled(fixedRate = 5000)
    public String execute() {
        logger.info("Executing scheduled task: PricePullScheduledTask");
        PriceInfo response;
        try {
            response = restTemplate.getForObject(priceUrl, PriceInfo.class);
            logger.info("Price: " + response.getPrice() + response.getTimestamp()); // TODO: fix log output
            return "response";
        } catch (Exception e) { // TODO: better exception handling
            logger.error("Error occurred when fetching src.price data: ", e);
        }
        return null;
    }
}
