package com.lcaohoanq.jvservice.domain.headquarter;

import com.lcaohoanq.jvservice.domain.headquarter.HeadquarterController.HeadquarterDTO;
import com.lcaohoanq.jvservice.domain.headquarter.HeadquarterPort.HeadquarterResponse;
import java.util.List;

public interface IHeadquarterService {
    
    List<HeadquarterResponse> getAll();

    List<HeadquarterResponse> create(List<HeadquarterDTO> headquarterList);

}
