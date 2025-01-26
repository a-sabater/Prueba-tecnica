package com.inditex.asset.infraestructure.api;

import com.inditex.asset.domain.Asset;
import com.inditex.asset.infraestructure.request.AssetFileUploadRequest;
import com.inditex.asset.infraestructure.response.AssetFileUploadResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The interface Asset api.
 */
@Tag(name = "asset", description = "the asset API")
public interface AssetApi {

	/**
	 * GET /api/mgmt/1/assets/ : Allows searching (&amp; filtering) all the
	 * uploaded/registered assets. Allows searching (&amp; filtering) all the
	 * uploaded/registered assets.
	 *
	 * @param uploadDateStart The start date for the range. (optional)
	 * @param uploadDateEnd   The end date for the range. (optional)
	 * @param filename        The filename expression for file filtering (regex). (optional)
	 * @param filetype        The file types for file filtering (one at a time). (optional)
	 * @param sortDirection   (optional)
	 * @return Returns a list of assets matching the specified criteria. (status         code 200) or Malformed request. (status code 400) or An unexpected         error occurred. (status code 500)
	 */
	@Operation(operationId = "getAssetsByFilter", summary = "Allows searching (& filtering) all the uploaded/registered assets.", description = "Allows searching (& filtering) all the uploaded/registered assets.", tags = {
			"asset"}, responses = {
					@ApiResponse(responseCode = "200", description = "Returns a list of assets matching the specified criteria.", content = {
							@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Asset.class)))}),
					@ApiResponse(responseCode = "400", description = "Malformed request."),
					@ApiResponse(responseCode = "500", description = "An unexpected error occurred.")})
	@RequestMapping(method = RequestMethod.GET, value = "/api/mgmt/1/assets/", produces = {"application/json"})

	Mono<ResponseEntity<Flux<Asset>>> getAssetsByFilter(
			@Parameter(name = "uploadDateStart", description = "The start date for the range.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "uploadDateStart", required = false) String uploadDateStart,
			@Parameter(name = "uploadDateEnd", description = "The end date for the range.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "uploadDateEnd", required = false) String uploadDateEnd,
			@Parameter(name = "filename", description = "The filename expression for file filtering (regex).", in = ParameterIn.QUERY) @Valid @RequestParam(value = "filename", required = false) String filename,
			@Parameter(name = "filetype", description = "The file types for file filtering (one at a time).", in = ParameterIn.QUERY) @Valid @RequestParam(value = "filetype", required = false) String filetype,
			@Parameter(name = "sortDirection", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "sortDirection", required = false) String sortDirection);

	/**
	 * POST /api/mgmt/1/assets/actions/upload : Performs an upload of the requested
	 * asset file. Performs an upload of the requested asset file.
	 *
	 * @param assetFileUploadRequest (required)
	 * @return The operation was accepted by the backend. (status code 202) or An         unexpected error occurred. (status code 500)
	 */
	@Operation(operationId = "uploadAssetFile", summary = "Performs an upload of the requested asset file.", description = "Performs an upload of the requested asset file.", tags = {
			"asset"}, responses = {
					@ApiResponse(responseCode = "202", description = "The operation was accepted by the backend.", content = {
							@Content(mediaType = "application/json", schema = @Schema(implementation = AssetFileUploadResponse.class))}),
					@ApiResponse(responseCode = "500", description = "An unexpected error occurred.")})
	@RequestMapping(method = RequestMethod.POST, value = "/api/mgmt/1/assets/actions/upload", produces = {
			"application/json"}, consumes = {"application/json"})

	Mono<ResponseEntity<AssetFileUploadResponse>> uploadAssetFile(
			@Parameter(name = "AssetFileUploadRequest", description = "", required = true) @Valid @RequestBody Mono<AssetFileUploadRequest> assetFileUploadRequest);

}
