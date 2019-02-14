package com.burahan.gdpdemo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Country
{
    private @Id @GeneratedValue Long id;
    private String countryName;
    private Long gdp;

    public Country()
    {
        // default constructor
    }

    public Country(String countryName, Long gdp)
    {
        this.countryName = countryName;
        this.gdp = gdp;
    }
}
