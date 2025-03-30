package com.lcaohoanq.jvservice.mapper;

import com.lcaohoanq.jvservice.domain.headquarter.Headquarter;
import com.lcaohoanq.jvservice.domain.headquarter.HeadquarterPort;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HeadquarterMapper {

    HeadquarterPort.HeadquarterResponse toHeadquarterResponse(Headquarter headquarter);

}
