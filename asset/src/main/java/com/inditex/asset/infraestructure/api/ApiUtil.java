package com.inditex.asset.infraestructure.api;

import com.inditex.asset.domain.Asset;
import com.inditex.asset.infraestructure.request.AssetFileUploadRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Util class for the api package
 */
@Slf4j
@Component
public class ApiUtil {
	/**
	 * Date formatter for pattern yyyy-MM-dd'T'HH:mm:ss
	 */
	public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	/**
	 * Url for the uploaded asset
	 */
	@Value("${file.properties.url}")
	String url;

	/**
	 * Maps Mono<AssetFileUploadRequest> to Mono<Asset>
	 *
	 * @param assetFileUploadRequest
	 *            the asset file upload request
	 * @return Mono<Asset> mono
	 */
	public Mono<Asset> mapAssetFileUploadRequest(Mono<AssetFileUploadRequest> assetFileUploadRequest) {
		final String id = UUID.randomUUID().toString();
		final OffsetDateTime date = LocalDateTime.now().atZone(ZoneId.systemDefault()).toOffsetDateTime();
		return assetFileUploadRequest.map(request -> Asset.builder().id(id).filename(request.getFilename())
				.url(UriComponentsBuilder.fromUriString(this.url).buildAndExpand(id).toString())
				.contentType(FilenameUtils.getExtension(request.getFilename()))
				.assetSize(request.getEncodedFile().length()).uploadDate(date.format(this.formatter)).build());
	}

}
