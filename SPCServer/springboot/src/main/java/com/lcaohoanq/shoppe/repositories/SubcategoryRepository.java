package com.lcaohoanq.shoppe.repositories;

import com.lcaohoanq.shoppe.models.Subcategory;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    Optional<Subcategory> findByName(String name);
    
    List<Subcategory> findByCategoryId(long categoryId);

    Boolean existsByCategoryIdAndNameIn(long categoryId, Set<String> names);


}
