package com.inditex.asset.infraestructure.api;

import com.inditex.asset.domain.Asset;
import com.inditex.asset.domain.usecase.CreateAssetsUseCase;
import com.inditex.asset.domain.usecase.FindAssetsUseCase;
import com.inditex.asset.infraestructure.request.AssetFileUploadRequest;
import com.inditex.asset.infraestructure.request.SortDirection;
import com.inditex.asset.infraestructure.response.AssetFileUploadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The type Asset controller.
 */
@Slf4j
@RestController
@RequestMapping("/api/mgmt/1/assets")
@RequiredArgsConstructor
public class AssetController implements AssetApi {

	private final CreateAssetsUseCase createAssetsUseCase;

	private final FindAssetsUseCase findAssetsUseCase;
	private final ApiUtil util;

	@Override
	@RequestMapping("/")
	public Mono<ResponseEntity<Flux<Asset>>> getAssetsByFilter(String uploadDateStart, String uploadDateEnd,
			String filename, String filetype, String sortDirection) {
		try {
			final SortDirection sort = SortDirection.valueOf(sortDirection);
			return Mono.just(ResponseEntity.ok(
					this.findAssetsUseCase.getAssetByFilter(uploadDateStart, uploadDateEnd, filename, filetype, sort)));
		} catch (final IllegalArgumentException e) {
			return Mono.just(ResponseEntity.badRequest().body(Flux.empty()));
		}
	}

	@Override
	@RequestMapping("/actions/upload")
	public Mono<ResponseEntity<AssetFileUploadResponse>> uploadAssetFile(
			Mono<AssetFileUploadRequest> assetFileUploadRequest) {
		final Mono<Asset> asset = this.util.mapAssetFileUploadRequest(assetFileUploadRequest);
		return this.createAssetsUseCase.createAsset(asset)
				.map(savedAsset -> (ResponseEntity.accepted().body(new AssetFileUploadResponse(savedAsset.getId()))))
				.switchIfEmpty(Mono.error(new RuntimeException("Failed to save asset")));
	}
}
