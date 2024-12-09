package com.lcaohoanq.shoppe.domain.category;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);
    
    Boolean existsByNameIn(List<String> names);

}
