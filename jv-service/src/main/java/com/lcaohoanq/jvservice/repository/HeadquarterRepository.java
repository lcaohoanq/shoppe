package com.lcaohoanq.jvservice.repository;

import com.lcaohoanq.jvservice.domain.headquarter.Headquarter;
import com.lcaohoanq.common.enums.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeadquarterRepository extends JpaRepository<Headquarter, Long> {

    Boolean existsByRegion(Country region);
    
}
