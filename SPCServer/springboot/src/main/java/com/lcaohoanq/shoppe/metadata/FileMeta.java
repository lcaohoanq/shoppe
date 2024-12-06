package com.lcaohoanq.shoppe.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileMeta {

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("file_size")
    private String fileSize;

    @JsonProperty("file_type")
    private String fileType;

    @Column(name = "image_url", length = 300)
    @JsonProperty("image_url")
    private String imageUrl;

    @Column(name = "video_url", length = 500)
    @JsonProperty("video_url")
    private String videoUrl;
}
