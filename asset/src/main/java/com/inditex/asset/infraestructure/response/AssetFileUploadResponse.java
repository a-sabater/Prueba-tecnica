package com.inditex.asset.infraestructure.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The response for an asset upload
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "AssetFileUploadResponse", description = "Represents a file upload response")
public class AssetFileUploadResponse {
    /**
     * Unique identifier for the requested file upload
     */
    @Schema(description = "Unique identifier for the requested file upload")
    private String id;
}
