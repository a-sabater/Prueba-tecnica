package com.inditex.asset.infraestructure.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The request to upload an asset
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "AssetFileUploadRequest", description = "Represents a file upload request.")
public class AssetFileUploadRequest {
    /**
     * The filename associated to the asset being uploaded
     */
    @Schema(description = "The filename associated to the asset being uploaded")
    private String filename;
    /**
     * The actual file to be uploaded.
     */
    @Schema(description = "The actual file to be uploaded.")
    private String encodedFile;
    /**
     * The filetype according to the MIME type (IANA) definition.
     */
    @Schema(description = "The filetype according to the MIME type (IANA) definition.")
    private String contentType;
}
