package com.lcaohoanq.shoppe.domain.headquarter;

import com.lcaohoanq.shoppe.domain.headquarter.HeadquarterController.HeadquarterDTO;
import com.lcaohoanq.shoppe.enums.Country;
import com.lcaohoanq.shoppe.exception.MalformBehaviourException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeadquarterService implements IHeadquarterService{

    private final HeadquarterRepository headquarterRepository;
    
    @Override
    public List<Headquarter> getAll() {
        return headquarterRepository.findAll();
    }

    @Override
    public List<Headquarter> create(List<HeadquarterDTO> headquarterList) {

        headquarterList.forEach(
            headquarterDTO -> {
                if (headquarterRepository.existsByRegion(headquarterDTO.region())) {
                    throw new MalformBehaviourException(String.format("Headquarter %s already exists", headquarterDTO.region()));
                }
            }
        );
        
        headquarterList.forEach(headquarterDTO -> {
            headquarterRepository.save(
                Headquarter.builder()
                    .region(headquarterDTO.region())
                    .domainUrl(headquarterDTO.domainUrl())
                    .build()
            );
        });
        
        return getAll();
    }
}
