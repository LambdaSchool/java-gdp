package com.lambdaschool.javagdp;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Country
{
    private @Id @GeneratedValue Long id;
    private String name;
    private double gdp;

    public Country()
    {
        // default constructor
    }

    public Country(String name, double gdp)
    {
        this.name = name;
        this.gdp = gdp;
    }
}

