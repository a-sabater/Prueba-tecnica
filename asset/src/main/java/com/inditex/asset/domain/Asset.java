package com.inditex.asset.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Asset is the business object
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Asset", description = "Represents an asset.")
public class Asset {
    /**
     * Unique file identifier.
     */
    @Schema(description = "Unique file identifier.")
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
    private String uploadDate;
}
