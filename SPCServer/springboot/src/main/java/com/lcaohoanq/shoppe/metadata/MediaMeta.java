package com.lcaohoanq.shoppe.metadata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.base.entity.BaseEntity;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
public class MediaMeta extends BaseEntity {

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("file_type")
    private String fileType;
    
    @JsonProperty("file_size")
    private Long fileSize;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("video_url")
    private String videoUrl;
    
}

