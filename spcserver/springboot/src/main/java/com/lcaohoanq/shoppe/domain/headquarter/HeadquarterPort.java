package com.lcaohoanq.shoppe.domain.headquarter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lcaohoanq.shoppe.enums.Country;
import java.time.LocalDateTime;

public interface HeadquarterPort {

    @JsonPropertyOrder({"id", "region", "domainUrl", "createdAt", "updatedAt"})
    record HeadquarterResponse(
        Long id,
        Country region,

        @JsonProperty("domain_url")
        String domainUrl,

        @JsonIgnore
        @JsonProperty("created_at")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
        LocalDateTime createdAt,

        @JsonIgnore
        @JsonProperty("updated_at")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
        LocalDateTime updatedAt
    ){}

}
