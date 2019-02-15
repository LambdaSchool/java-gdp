package com.burahan.gdpdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/countries/total")
    public Long returnGDPTotal()
    {
        List<Country> cList = cRepo.findAll();
        Long runningTotal = 0L;
        for (Country c : cList)
        {
//            System.out.println(c.getGdp());
            runningTotal += c.getGdp();
        }
        return runningTotal;
    }

    /*
        vv Csaba's Code vv
        Ask John about why creating a new instance of
        Country in the route below, that there is no id
        or why the id field is null
     */
//    @GetMapping("/countries/total")
//    public List<Country> economy2() {
//        List<Country> totalGDP = new ArrayList<>();
//
//        Country all = new Country();
//
//        Long total = 0L;
//        for (Country g: cRepo.findAll()) {
//            total += g.getGdp();
//        }
//
//        all.setCountry("Total");
//        all.setGdp(total);
//        System.out.println(all.getId());
//        totalGDP.add(all);
//        return totalGDP;
//    }

    @GetMapping("/gdp/{country}")
    public ObjectNode findOneByGDP(@PathVariable String country)
    {
        List<Country> cList = cRepo.findAll();
//        Country foundCountry;
        for (Country c : cList)
        {
            if (c.getCountry().equalsIgnoreCase(country))
            {
//                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//                return ow.writeValueAsString(c)
                System.out.println(c);
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode totalGDP = mapper.createObjectNode();
                totalGDP.put("id", c.getId());
                totalGDP.put("country", c.getCountry());
                totalGDP.put("gdp", c.getGdp());

                CountryLog message = new CountryLog("Checked GDP of " + c.getCountry());
                rt.convertAndSend(GdpDemoApplication.QUEUE_NAME, message.toString());
                log.info("Your presence has been noted");

                return totalGDP;
            }
        }


        return null;
    }

}
