package com.inditex.asset.infraestructure.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

/**
 * The Entity used in the database
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("ASSET")
public class AssetEntity {
    /**
     * Unique file identifier.
     */
    @Id
    @Schema(description = "Unique file identifier.")
    @GeneratedValue(generator = "uuid")
    private String id;
    /**
     * Name of the asset.
     */
    @Schema(description = "Name of the asset.")
    private String filename;
    /**
     * The file type.
     */
    @Schema(description = "The file type.")
    private String contentType;
    /**
     * The URL of the uploaded/published asset.
     */
    @Schema(description = "The URL of the uploaded/published asset.")
    private String url;
    /**
     * The actual file size.
     */
    @Schema(description = "The actual file size.")
    @JsonProperty("size")
    private Integer assetSize;
    /**
     * The date & time the file was uploaded.
     */
    @Schema(description = "The date & time the file was uploaded.")
    private OffsetDateTime uploadDate;
}
