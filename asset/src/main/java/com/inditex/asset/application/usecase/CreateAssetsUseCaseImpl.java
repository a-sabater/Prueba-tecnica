package com.inditex.asset.application.usecase;

import com.inditex.asset.domain.Asset;
import com.inditex.asset.domain.AssetException;
import com.inditex.asset.domain.repository.AssetRepository;
import com.inditex.asset.domain.usecase.CreateAssetsUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * The type Create assets use case.
 */
@Slf4j
@Component
@AllArgsConstructor
public class CreateAssetsUseCaseImpl implements CreateAssetsUseCase {

	private final AssetRepository repository;

	@Override
	public Mono<Asset> createAsset(Mono<Asset> asset) {
		return asset.flatMap(this.repository::save)
				.onErrorMap(e -> new AssetException("Error saving asset: " + asset, e));
	}
}
