package com.lambdaschool.javagdp;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;



// findAll and all that stuff avail now.
public interface CountryRepo extends JpaRepository<Country, Long> {
        Country findByCountry(String country);
}
