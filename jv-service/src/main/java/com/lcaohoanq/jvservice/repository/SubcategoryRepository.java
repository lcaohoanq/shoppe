package com.lcaohoanq.jvservice.repository;

import com.lcaohoanq.jvservice.domain.subcategory.Subcategory;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    Optional<Subcategory> findByName(String name);
    
    List<Subcategory> findByCategoryId(long categoryId);

    Boolean existsByCategoryIdAndNameIn(long categoryId, Set<String> names);


}
