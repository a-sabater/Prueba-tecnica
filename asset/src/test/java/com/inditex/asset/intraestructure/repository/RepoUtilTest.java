package com.inditex.asset.intraestructure.repository;

import com.inditex.asset.domain.Asset;
import com.inditex.asset.infraestructure.entity.AssetEntity;
import com.inditex.asset.infraestructure.repository.RepoUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class RepoUtilTest {

    private RepoUtil repoUtil;

    @BeforeEach
    public void setUp() {
        repoUtil = new RepoUtil();
    }

    @Test
    public void testTransformParameterDate_ValidDate() {
        String date = "2023-10-01T10:15:30";
        OffsetDateTime expected = LocalDateTime.parse(date, repoUtil.formatter).atZone(ZoneId.systemDefault()).toOffsetDateTime();
        OffsetDateTime result = repoUtil.transformParameterDate(date);
        assertEquals(expected, result);
    }

    @Test
    public void testTransformParameterDate_InvalidDate() {
        String date = "invalid_date";
        assertThrows(RuntimeException.class, () -> repoUtil.transformParameterDate(date));
    }

    @Test
    public void testMapEntitiesToAssets() {
        AssetEntity assetEntity = AssetEntity.builder()
                .id("1")
                .filename("file1.jpg")
                .contentType("image/jpeg")
                .url("http://example.com/file1.jpg")
                .assetSize(1024)
                .uploadDate(OffsetDateTime.now())
                .build();
        Flux<AssetEntity> assetEntityFlux = Flux.just(assetEntity);

        Flux<Asset> result = repoUtil.mapEntitiesToAssets(assetEntityFlux);

        assertNotNull(result);
        Asset asset = result.blockFirst();
        assertNotNull(asset);
        assertEquals(assetEntity.getId(), asset.getId());
        assertEquals(assetEntity.getFilename(), asset.getFilename());
        assertEquals(assetEntity.getContentType(), asset.getContentType());
        assertEquals(assetEntity.getUrl(), asset.getUrl());
    }

    @Test
    public void testMapEntityToAsset() {
        AssetEntity assetEntity = AssetEntity.builder()
                .id("1")
                .filename("file1.jpg")
                .contentType("image/jpeg")
                .url("http://example.com/file1.jpg")
                .assetSize(1024)
                .uploadDate(OffsetDateTime.now())
                .build();
        Mono<AssetEntity> assetEntityMono = Mono.just(assetEntity);

        Mono<Asset> result = repoUtil.mapEntityToAsset(assetEntityMono);
        Asset asset = result.block();

        assertNotNull(asset);
        assertEquals(assetEntity.getId(), asset.getId());
        assertEquals(assetEntity.getFilename(), asset.getFilename());
        assertEquals(assetEntity.getContentType(), asset.getContentType());
        assertEquals(assetEntity.getUrl(), asset.getUrl());
    }

    @Test
    public void testMapAssetToEntity() {
        Asset asset = Asset.builder()
                .id("1")
                .filename("file1.jpg")
                .contentType("image/jpeg")
                .url("http://example.com/file1.jpg")
                .assetSize(1024)
                .uploadDate("2023-10-01T10:15:30")
                .build();

        AssetEntity result = repoUtil.mapAssetToEntity(asset);

        assertNotNull(result);
        assertEquals(asset.getId(), result.getId());
        assertEquals(asset.getFilename(), result.getFilename());
        assertEquals(asset.getContentType(), result.getContentType());
        assertEquals(asset.getUrl(), result.getUrl());
    }
}
