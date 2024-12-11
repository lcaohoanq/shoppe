package com.lcaohoanq.shoppe.domain.headquarter;

import com.lcaohoanq.shoppe.enums.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeadquarterRepository extends JpaRepository<Headquarter, Long> {

    Boolean existsByRegion(Country region);
    
}
