package com.lcaohoanq.shoppe.domain.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.base.entity.BaseEntity;
import com.lcaohoanq.shoppe.metadata.MediaMeta;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "attachments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Attachment {

    @Id
    @SequenceGenerator(name = "attachments_seq", sequenceName = "attachments_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attachments_seq")
    @Column(name="id", unique=true, nullable=false)
    @JsonProperty("id")
    private Long id;
    
    @Embedded
    private MediaMeta mediaMeta;
    
}
