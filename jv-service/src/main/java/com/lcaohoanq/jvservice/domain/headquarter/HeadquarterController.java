package com.lcaohoanq.jvservice.domain.headquarter;

import com.lcaohoanq.jvservice.api.ApiResponse;
import com.lcaohoanq.jvservice.constant.Regex;
import com.lcaohoanq.jvservice.domain.headquarter.HeadquarterPort.HeadquarterResponse;
import com.lcaohoanq.common.enums.Country;
import com.lcaohoanq.jvservice.exception.MethodArgumentNotValidException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/headquarters")
@RequiredArgsConstructor
@Slf4j
public class HeadquarterController {

    private final IHeadquarterService headquarterService;
    
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<HeadquarterResponse>>> getAll(){
        return ResponseEntity.ok(
            ApiResponse.<List<HeadquarterResponse>>builder()
                .message("Get all headquarters successfully")
                .statusCode(HttpStatus.OK.value())
                .isSuccess(true)
                .data(headquarterService.getAll())
                .build());
    }
    
    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<List<HeadquarterResponse>>> create(
        @Valid @RequestBody List<HeadquarterDTO> headquarterList,
        BindingResult result
    ){
        if(result.hasErrors()){
            throw new MethodArgumentNotValidException(result);
        }
        
        return ResponseEntity.ok(
            ApiResponse.<List<HeadquarterResponse>>builder()
                .message("Create headquarter successfully")
                .statusCode(HttpStatus.OK.value())
                .isSuccess(true)
                .data(headquarterService.create(headquarterList))
                .build());
    }
    
    public record HeadquarterDTO(
       
        Country region,
        @Pattern(regexp = Regex.HEADQUARTER_DOMAIN_URL_REGEX) String domainUrl
        
    ) {}
    
}
