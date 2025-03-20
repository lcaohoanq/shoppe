package com.lcaohoanq.shoppe.domain.headquarter;

import com.lcaohoanq.shoppe.domain.headquarter.HeadquarterController.HeadquarterDTO;
import com.lcaohoanq.shoppe.domain.headquarter.HeadquarterPort.HeadquarterResponse;
import java.util.List;

public interface IHeadquarterService {
    
    List<HeadquarterResponse> getAll();

    List<HeadquarterResponse> create(List<HeadquarterDTO> headquarterList);

}
