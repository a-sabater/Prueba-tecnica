package com.inditex.asset.domain.repository;

import com.inditex.asset.domain.Asset;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * The interface Asset repository.
 */
public interface AssetRepository {

	/**
	 * Find flux of assets by upload date with order desc
	 *
	 * @param uploadDateStart the upload date start
	 * @param uploadDateEnd   the upload date end
	 * @return <Flux<Asset>>
	 */
	Optional<Flux<Asset>> findAllByUploadDateDesc(String uploadDateStart, String uploadDateEnd);

	/**
	 * Find flux of assets by upload date with order asc
	 *
	 * @param uploadDateStart the upload date start
	 * @param uploadDateEnd   the upload date end
	 * @return <Flux<Asset>>
	 */
	Optional<Flux<Asset>> findAllByUploadDateAsc(String uploadDateStart, String uploadDateEnd);

	/**
	 * Save an asset in database
	 *
	 * @param asset the asset
	 * @return Mono<Asset>  mono
	 */
	Mono<Asset> save(Asset asset);
}
