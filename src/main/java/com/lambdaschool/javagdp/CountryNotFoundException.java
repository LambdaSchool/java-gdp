package com.lambdaschool.javagdp;

public class CountryNotFoundException extends  RuntimeException {
    public CountryNotFoundException() {
        super("Could not find Country SMH");
    }
}
