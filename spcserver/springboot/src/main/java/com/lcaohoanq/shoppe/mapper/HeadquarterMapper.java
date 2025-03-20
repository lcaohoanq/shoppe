package com.lcaohoanq.shoppe.mapper;

import com.lcaohoanq.shoppe.domain.headquarter.Headquarter;
import com.lcaohoanq.shoppe.domain.headquarter.HeadquarterPort;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HeadquarterMapper {

    HeadquarterPort.HeadquarterResponse toHeadquarterResponse(Headquarter headquarter);

}
