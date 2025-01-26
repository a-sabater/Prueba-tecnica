package com.inditex.asset.infraestructure.repository;

import com.inditex.asset.domain.Asset;
import com.inditex.asset.domain.repository.AssetRepository;
import com.inditex.asset.infraestructure.entity.AssetEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * The type Data asset repository.
 */
@Slf4j
@Repository
@Primary
public class DataAssetRepository implements AssetRepository {

	private final R2dbcAssetRepository r2dbcAssetRepository;
	private final RepoUtil util;

	/**
	 * Instantiates a new Data asset repository.
	 *
	 * @param r2dbcAssetRepository
	 *            the r 2 dbc asset repository
	 * @param util
	 *            the util
	 */
	public DataAssetRepository(final R2dbcAssetRepository r2dbcAssetRepository, RepoUtil util) {
		this.r2dbcAssetRepository = r2dbcAssetRepository;
		this.util = util;
	}

	@Override
	public Optional<Flux<Asset>> findAllByUploadDateDesc(String uploadDateStart, String uploadDateEnd) {
		final Optional<Flux<AssetEntity>> resultFromRepo = Optional.of(this.r2dbcAssetRepository.findAllByDateDesc(
				this.util.transformParameterDate(uploadDateStart), this.util.transformParameterDate(uploadDateEnd)));
		return Optional.of(this.util.mapEntitiesToAssets(resultFromRepo.orElse(Flux.empty())));
	}

	@Override
	public Optional<Flux<Asset>> findAllByUploadDateAsc(String uploadDateStart, String uploadDateEnd) {
		final Optional<Flux<AssetEntity>> resultFromRepo = Optional.of(this.r2dbcAssetRepository.findAllByDateAsc(
				this.util.transformParameterDate(uploadDateStart), this.util.transformParameterDate(uploadDateEnd)));
		return Optional.of(this.util.mapEntitiesToAssets(resultFromRepo.orElse(Flux.empty())));
	}

	@Transactional
	@Override
	public Mono<Asset> save(Asset asset) {
		final AssetEntity assetEnt = this.util.mapAssetToEntity(asset);
		final Mono<AssetEntity> resultFromRepo = this.r2dbcAssetRepository.save(assetEnt);
		return this.util.mapEntityToAsset(resultFromRepo);
	}

}
