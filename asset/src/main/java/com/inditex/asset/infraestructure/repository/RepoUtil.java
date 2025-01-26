package com.inditex.asset.infraestructure.repository;

import com.inditex.asset.domain.Asset;
import com.inditex.asset.infraestructure.entity.AssetEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Util class for the repository
 */
@Slf4j
@Component
public class RepoUtil {

	/**
	 * The Formatter.
	 */
	public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

	/**
	 * Transform parameter date offset date time.
	 *
	 * @param date
	 *            the date
	 * @return the offset date time
	 */
	public OffsetDateTime transformParameterDate(String date) {

		final LocalDateTime dateTime = LocalDateTime.parse(date, this.formatter);
		final ZoneId zoneId = ZoneId.systemDefault();
		return dateTime.atZone(zoneId).toOffsetDateTime();
	}

	/**
	 * Maps Flux<AssetEntity> to Flux<Asset>
	 *
	 * @param assetEntityFlux
	 *            the flux of AssetEntity
	 * @return Flux<Asset> flux
	 */
	public Flux<Asset> mapEntitiesToAssets(Flux<AssetEntity> assetEntityFlux) {
		return assetEntityFlux.map(entity -> Asset.builder().id(entity.getId()).filename(entity.getFilename())
				.contentType(entity.getContentType()).url(entity.getUrl()).assetSize(entity.getAssetSize())
				.uploadDate(entity.getUploadDate().format(this.formatter)).build());
	}

	/**
	 * Maps Mono<AssetEntity> to Mono<Asset>
	 *
	 * @param assetEntityMono
	 *            the mono of AssetEntity
	 * @return Mono<Asset> mono
	 */
	public Mono<Asset> mapEntityToAsset(Mono<AssetEntity> assetEntityMono) {
		return assetEntityMono.map(entity -> Asset.builder().id(entity.getId()).filename(entity.getFilename())
				.contentType(entity.getContentType()).url(entity.getUrl()).assetSize(entity.getAssetSize())
				.uploadDate(entity.getUploadDate().format(this.formatter)).build());
	}

	/**
	 * Maps Asset to AssetEntity
	 *
	 * @param asset
	 *            the asset
	 * @return AssetEntity asset entity
	 */
	public AssetEntity mapAssetToEntity(Asset asset) {
		return AssetEntity.builder().id(asset.getId()).filename(asset.getFilename()).contentType(asset.getContentType())
				.url(asset.getUrl()).assetSize(asset.getAssetSize())
				.uploadDate(this.transformParameterDate(asset.getUploadDate())).build();
	}
}
