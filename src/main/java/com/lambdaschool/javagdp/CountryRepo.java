package com.lambdaschool.javagdp;

import org.springframework.data.jpa.repository.JpaRepository;

// findAll and all that stuff avail now.
public interface CountryRepo extends JpaRepository<Country, Long> {

}
