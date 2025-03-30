package com.lcaohoanq.jvservice.domain.headquarter;

import com.lcaohoanq.jvservice.domain.headquarter.HeadquarterController.HeadquarterDTO;
import com.lcaohoanq.jvservice.domain.headquarter.HeadquarterPort.HeadquarterResponse;
import com.lcaohoanq.jvservice.exception.MalformBehaviourException;
import com.lcaohoanq.jvservice.mapper.HeadquarterMapper;
import com.lcaohoanq.jvservice.repository.HeadquarterRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeadquarterService implements IHeadquarterService {

    private final HeadquarterRepository headquarterRepository;
    private final HeadquarterMapper headquarterMapper;

    @Override
    public List<HeadquarterResponse> getAll() {
//        return headquarterRepository.findAll().stream().map(
//            headquarter -> new HeadquarterPort.HeadquarterResponse(
//                headquarter.getId(),
//                headquarter.getRegion(),
//                headquarter.getDomainUrl(),
//                headquarter.getCreatedAt(),
//                headquarter.getUpdatedAt()
//            )
//        ).toList();

        return headquarterRepository.findAll().stream()
            .map(headquarterMapper::toHeadquarterResponse).toList();
    }

    @Override
    public List<HeadquarterResponse> create(List<HeadquarterDTO> headquarterList) {

        headquarterList.forEach(
            headquarterDTO -> {
                if (headquarterRepository.existsByRegion(headquarterDTO.region())) {
                    throw new MalformBehaviourException(
                        String.format("Headquarter %s already exists", headquarterDTO.region()));
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
