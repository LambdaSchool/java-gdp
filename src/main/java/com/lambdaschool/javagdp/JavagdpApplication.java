package com.lambdaschool.javagdp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class JavagdpApplication {

    // the server
    public static final String EXCHANGE_NAME = "Country";
    // This is where the masseges go
    public static final String QUEUE_NAME = "Log";
    public static void main(String[] args) {
        SpringApplication.run(JavagdpApplication.class, args);
    }
    @Bean
    public TopicExchange appExchange(){
        return new TopicExchange(EXCHANGE_NAME);
    }
    @Bean
    public Queue appQueue(){
        return new Queue(QUEUE_NAME);
    }
    @Bean
    public Binding declareBinding(){
        return BindingBuilder.bind(appQueue()).to(appExchange()).with(QUEUE_NAME);
    }

    // Working with Json and the message QUEUE
    @Bean
    public RabbitTemplate rt(final ConnectionFactory cf){
        final RabbitTemplate rt = new RabbitTemplate(cf);
        rt.setMessageConverter(producerJackson2MessageConverter());
        return rt;
    }
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

