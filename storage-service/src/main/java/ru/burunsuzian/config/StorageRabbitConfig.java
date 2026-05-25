package ru.burunsuzian.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ru.burunsuzian.config.RabbitMqConfig.EXCHANGE_ORDER;
import static ru.burunsuzian.config.RabbitMqConfig.QUEUE_STORAGE_APPROVAL;

@Configuration
public class StorageRabbitConfig extends RabbitMqConfig {
    @Bean
    public Queue storageApprovalQueue() {
        return new Queue(QUEUE_STORAGE_APPROVAL);
    }

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(EXCHANGE_ORDER);
    }

    @Bean
    public Binding bindingStorageApproval(Queue storageApprovalQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(storageApprovalQueue).to(orderExchange).with("order.paid");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
