package com.inditex.asset.domain.usecase;

import com.inditex.asset.domain.Asset;
import reactor.core.publisher.Mono;

/**
 * The interface Create assets use case.
 */
public interface CreateAssetsUseCase {

	/**
	 * Create Asset
	 *
	 * @param asset the asset
	 * @return Mono<Asset> mono
	 */
	Mono<Asset> createAsset(Mono<Asset> asset);

}
