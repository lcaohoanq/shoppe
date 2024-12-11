package com.lcaohoanq.shoppe.domain.headquarter;

import com.lcaohoanq.shoppe.domain.headquarter.HeadquarterController.HeadquarterDTO;
import java.util.List;

public interface IHeadquarterService {
    
    List<Headquarter> getAll();

    List<Headquarter> create(List<HeadquarterDTO> headquarterList);

}
