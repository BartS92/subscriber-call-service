package subscriber.call.group.bff.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {
    @Value("${rabbitmq-settings.exchange}")
    private String exchange;

    @Value("${rabbitmq-settings.queues.start-call}")
    private String startCallQueueName;

    @Value("${rabbitmq-settings.routing-keys.start-call}")
    private String startCallRoutingKey;

    @Value("${rabbitmq-settings.queues.finish-call}")
    private String finishCallQueueName;

    @Value("${rabbitmq-settings.routing-keys.finish-call}")
    private String finishCallRoutingKey;


    @Bean
    Queue finishCallQueue() {
        return new Queue(finishCallQueueName, false);
    }

    @Bean
    Queue startCallQueue() {
        return new Queue(startCallQueueName, false);
    }


    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    Binding startCallBinding() {
        return BindingBuilder.bind(startCallQueue()).to(exchange()).with(startCallRoutingKey);
    }

    @Bean
    Binding finishCallBinding() {
        return BindingBuilder.bind(finishCallQueue()).to(exchange()).with(finishCallRoutingKey);
    }


    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

}
