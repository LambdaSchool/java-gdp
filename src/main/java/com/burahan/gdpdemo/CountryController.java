package com.burahan.gdpdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/countries")
    public List<Country> all()
    {
        return cRepo.findAll();
    }

    @PostMapping("/countries")
    public List<Country> newCountry(@RequestBody List<Country> newCountries)
    {
        return cRepo.saveAll(newCountries);
    }

    @GetMapping("/countries/names")
    public List<Country> allNamesSorted()
    {
        List<Country> cList = cRepo.findAll();
        Collections.sort(cList, (c1, c2) -> c1.getCountry().compareToIgnoreCase(c2.getCountry()));
        return cList;
    }

    @GetMapping("/countries/economy")
    public List<Country> allGDPSorted()
    {
        return cRepo.findAll().stream().sorted(Comparator.comparing(Country::getGdp)).collect(Collectors.toList());
    }

}
