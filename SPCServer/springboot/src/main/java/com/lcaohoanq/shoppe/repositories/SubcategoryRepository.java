package com.lcaohoanq.shoppe.repositories;

import com.lcaohoanq.shoppe.models.Category;
import com.lcaohoanq.shoppe.models.Subcategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    Optional<Subcategory> findByName(String name);
    
    List<Subcategory> findByCategoryId(long categoryId);
    
    Boolean existsByNameIn(List<String> names);
    
}
