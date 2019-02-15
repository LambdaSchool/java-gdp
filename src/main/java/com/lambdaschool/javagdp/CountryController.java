package com.lambdaschool.javagdp;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;


import java.util.Comparator;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class CountryController {
    private final CountryRepo countryRepos;
    private final RabbitTemplate rt;

    public CountryController(CountryRepo countryRepos, RabbitTemplate rt) {
        this.countryRepos = countryRepos;
        this.rt = rt;
    }

    @GetMapping("/names")
    public List<Country> all() {
        return countryRepos.findAll();
    }

    @GetMapping("/economy")
    public List<Country> economy() {
        return countryRepos.findAll().stream().sorted(Comparator.comparing(Country::getGdp).reversed()).collect(Collectors.toList());
    }

    @GetMapping("/total")
    public List<Country> economy2() {
        List<Country> totalGDP = new ArrayList<>();
        Country all = new Country();
        Long newId = 0L;
         Long total = 0L;
        for (Country g: countryRepos.findAll()) {
            if (g.getId() > newId) {
                newId = g.getId();
            }
            total += g.getGdp();
        }
        total += 1L;
        all.setCountry("Total");
        all.setGdp(total);
        all.setId(newId);
        totalGDP.add(all);
        return totalGDP;

    }

    @PostMapping("/gdp")
    public List<Country> newCountry(@RequestBody List<Country> newCountries){
        return countryRepos.saveAll(newCountries);
    }

    @GetMapping("/gdp/{country}")
    public List<Country> findCountry(@PathVariable String country) {
        List<Country> byName = new ArrayList<>();
         byName.add(countryRepos.findByCountry(country));
        return byName;
    }
}
