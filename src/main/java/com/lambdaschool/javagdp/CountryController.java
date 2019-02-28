package com.lambdaschool.javagdp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@Slf4j
@RestController

public class CountryController {
    private final CountryRepository countryRepository;
    private final RabbitTemplate rabbitTemplate;

    public CountryController(CountryRepository countryRepository, RabbitTemplate rabbitTemplate) {
        this.countryRepository = countryRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/names")
    public List<Country> getAll(){
        List<Country> countryList = countryRepository.findAll();
        countryList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return countryList;
    }

    @GetMapping("/economy")
    public List<Country> economy() {
        List<Country> countryList = countryRepository.findAll();
        countryList.sort((c1, c2) -> (int) (c2.getGdp() - c1.getGdp()));
        return countryList;
    }



}