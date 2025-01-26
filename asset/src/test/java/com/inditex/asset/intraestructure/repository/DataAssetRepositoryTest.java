package com.inditex.asset.intraestructure.repository;

import com.inditex.asset.domain.Asset;
import com.inditex.asset.infraestructure.entity.AssetEntity;
import com.inditex.asset.infraestructure.repository.DataAssetRepository;
import com.inditex.asset.infraestructure.repository.R2dbcAssetRepository;
import com.inditex.asset.infraestructure.repository.RepoUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DataAssetRepositoryTest {

    @Mock
    private R2dbcAssetRepository r2dbcAssetRepository;

    @Mock
    private RepoUtil util;

    private DataAssetRepository dataAssetRepository;

    @BeforeEach
    void setUp() {
        dataAssetRepository = new DataAssetRepository(r2dbcAssetRepository, util);
    }

    @Test
    void testFindAllByUploadDateDesc() {
        String uploadDateStartStr = "2022-01-01T00:00:00Z";
        String uploadDateEndStr = "2022-01-31T23:59:59Z";
        OffsetDateTime uploadDateStart = OffsetDateTime.parse(uploadDateStartStr);
        OffsetDateTime uploadDateEnd = OffsetDateTime.parse(uploadDateEndStr);
        AssetEntity assetEntity = new AssetEntity();
        Asset asset = new Asset();

        when(util.transformParameterDate(uploadDateStartStr)).thenReturn(uploadDateStart);
        when(util.transformParameterDate(uploadDateEndStr)).thenReturn(uploadDateEnd);
        when(r2dbcAssetRepository.findAllByDateDesc(uploadDateStart, uploadDateEnd)).thenReturn(Flux.just(assetEntity));
        when(util.mapEntitiesToAssets(any())).thenReturn(Flux.just(asset));

        Optional<Flux<Asset>> result = dataAssetRepository.findAllByUploadDateDesc(uploadDateStartStr, uploadDateEndStr);

        assertEquals(1, result.get().count().block());
        verify(util).transformParameterDate(uploadDateStartStr);
        verify(util).transformParameterDate(uploadDateEndStr);
        verify(r2dbcAssetRepository).findAllByDateDesc(uploadDateStart, uploadDateEnd);
        verify(util).mapEntitiesToAssets(any());
    }

    @Test
    void testFindAllByUploadDateAsc() {
        String uploadDateStartStr = "2022-01-01T00:00:00Z";
        String uploadDateEndStr = "2022-01-31T23:59:59Z";
        OffsetDateTime uploadDateStart = OffsetDateTime.parse(uploadDateStartStr);
        OffsetDateTime uploadDateEnd = OffsetDateTime.parse(uploadDateEndStr);
        AssetEntity assetEntity = new AssetEntity();
        Asset asset = new Asset();

        when(util.transformParameterDate(uploadDateStartStr)).thenReturn(uploadDateStart);
        when(util.transformParameterDate(uploadDateEndStr)).thenReturn(uploadDateEnd);
        when(r2dbcAssetRepository.findAllByDateAsc(uploadDateStart, uploadDateEnd)).thenReturn(Flux.just(assetEntity));
        when(util.mapEntitiesToAssets(any())).thenReturn(Flux.just(asset));

        Optional<Flux<Asset>> result = dataAssetRepository.findAllByUploadDateAsc(uploadDateStartStr, uploadDateEndStr);

        assertEquals(1, result.get().count().block());
        verify(util).transformParameterDate(uploadDateStartStr);
        verify(util).transformParameterDate(uploadDateEndStr);
        verify(r2dbcAssetRepository).findAllByDateAsc(uploadDateStart, uploadDateEnd);
        verify(util).mapEntitiesToAssets(any());
    }

    @Test
    void testSave() {
        Asset asset = new Asset();
        AssetEntity assetEntity = new AssetEntity();

        when(util.mapAssetToEntity(asset)).thenReturn(assetEntity);
        when(r2dbcAssetRepository.save(assetEntity)).thenReturn(Mono.just(assetEntity));
        when(util.mapEntityToAsset(any())).thenReturn(Mono.just(asset));

        Mono<Asset> result = dataAssetRepository.save(asset);

        assertEquals(asset, result.block());
        verify(util).mapAssetToEntity(asset);
        verify(r2dbcAssetRepository).save(assetEntity);
        verify(util).mapEntityToAsset(any());
    }

    @Test
    void testFindAllByUploadDateDescWithEmptyResult() {
        String uploadDateStartStr = "2022-01-01T00:00:00Z";
        String uploadDateEndStr = "2022-01-31T23:59:59Z";
        OffsetDateTime uploadDateStart = OffsetDateTime.parse(uploadDateStartStr);
        OffsetDateTime uploadDateEnd = OffsetDateTime.parse(uploadDateEndStr);

        when(util.transformParameterDate(uploadDateStartStr)).thenReturn(uploadDateStart);
        when(util.transformParameterDate(uploadDateEndStr)).thenReturn(uploadDateEnd);
        when(r2dbcAssetRepository.findAllByDateDesc(uploadDateStart, uploadDateEnd)).thenReturn(Flux.empty());
        when(util.mapEntitiesToAssets(any())).thenReturn(Flux.empty());

        Optional<Flux<Asset>> result = dataAssetRepository.findAllByUploadDateDesc(uploadDateStartStr, uploadDateEndStr);

        assertEquals(0, result.get().count().block());
        verify(util).transformParameterDate(uploadDateStartStr);
        verify(util).transformParameterDate(uploadDateEndStr);
        verify(r2dbcAssetRepository).findAllByDateDesc(uploadDateStart, uploadDateEnd);
        verify(util).mapEntitiesToAssets(any());
    }

}

