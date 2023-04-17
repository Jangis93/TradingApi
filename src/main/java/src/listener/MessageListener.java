package src.listener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import src.execution.OrderExecutionService;
import src.price.PriceInfo;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
@AllArgsConstructor
@Slf4j
public class MessageListener {

    private OrderExecutionService orderExecutionService;

    @JmsListener(destination = "${price.jms.topic}", containerFactory = "priceJmsContFactory")
    public void getListener(Message message) {
        try {
            PriceInfo newPrice = (PriceInfo) ((ActiveMQObjectMessage) message).getObject();
            log.info("Consumer discovered new price: {}", newPrice.getPrice());
            orderExecutionService.handleOrders(newPrice);
        } catch (JMSException e) {
            log.error("Failed to retrieve new message from queue", e);
        }
    }
}
