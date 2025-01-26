package com.inditex.asset.application.usecase;

import com.inditex.asset.domain.Asset;
import com.inditex.asset.domain.AssetException;
import com.inditex.asset.domain.repository.AssetRepository;
import com.inditex.asset.domain.usecase.FindAssetsUseCase;
import com.inditex.asset.infraestructure.request.SortDirection;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * The type Find assets use case.
 */
@Slf4j
@Component
@AllArgsConstructor
public class FindAssetsUseCaseImpl implements FindAssetsUseCase {

	private final AssetRepository assetRepository;

	@Override
	public Flux<Asset> getAssetByFilter(String uploadDateStart, String uploadDateEnd, String filename, String filetype,
			SortDirection sortDirection) {
		Flux<Asset> response = null;
		switch (sortDirection) {
			case ASC -> response = this.assetRepository.findAllByUploadDateAsc(uploadDateStart, uploadDateEnd)
					.orElseThrow(() -> new AssetException("Error retrieving list of assets"));
			case DESC -> response = this.assetRepository.findAllByUploadDateDesc(uploadDateStart, uploadDateEnd)
					.orElseThrow(() -> new AssetException("Error retrieving list of assets"));
		}
		return response != null ? response : Flux.empty();
	}
}
