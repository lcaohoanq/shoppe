package com.lcaohoanq.shoppe.domain.headquarter;

import com.lcaohoanq.shoppe.domain.headquarter.HeadquarterController.HeadquarterDTO;
import com.lcaohoanq.shoppe.domain.headquarter.HeadquarterPort.HeadquarterResponse;
import com.lcaohoanq.shoppe.exception.MalformBehaviourException;
import com.lcaohoanq.shoppe.mapper.HeadquarterMapper;
import com.lcaohoanq.shoppe.repository.HeadquarterRepository;
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
