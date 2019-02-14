package com.burahan.gdpdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CountryController
{
    private final CountryRepository cRepo;
    private final RabbitTemplate rt;

    public CountryController(CountryRepository cRepo, RabbitTemplate rt)
    {
        this.cRepo = cRepo;
        this.rt = rt;
    }


}
