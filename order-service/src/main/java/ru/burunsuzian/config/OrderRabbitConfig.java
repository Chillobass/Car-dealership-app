package ru.burunsuzian.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ru.burunsuzian.config.RabbitMqConfig.EXCHANGE_STORAGE;
import static ru.burunsuzian.config.RabbitMqConfig.QUEUE_ORDER_RESULT;

@Configuration
public class OrderRabbitConfig extends RabbitMqConfig {
    @Bean
    public Queue orderResultQueue() {
        return new Queue(QUEUE_ORDER_RESULT);
    }

    @Bean
    public TopicExchange storageExchange() {
        return new TopicExchange(EXCHANGE_STORAGE);
    }

    @Bean
    public Binding bindingOrderResult(Queue orderResultQueue, TopicExchange storageExchange) {
        return BindingBuilder.bind(orderResultQueue).to(storageExchange).with("storage.result");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
