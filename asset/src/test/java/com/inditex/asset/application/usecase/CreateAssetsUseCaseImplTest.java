package com.inditex.asset.application.usecase;

import com.inditex.asset.domain.Asset;
import com.inditex.asset.domain.AssetException;
import com.inditex.asset.domain.repository.AssetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateAssetsUseCaseImplTest {

    @Mock
    private AssetRepository repository;

    @InjectMocks
    private CreateAssetsUseCaseImpl createAssetsUseCase;

    @Test
    void testCreateAsset_success() {
        Asset asset = new Asset();
        when(repository.save(asset)).thenReturn(Mono.just(asset));

        Mono<Asset> result = createAssetsUseCase.createAsset(Mono.just(asset));

        assertEquals(asset, result.block());
        verify(repository).save(asset);
    }

    @Test
    void testCreateAsset_handleNullAsset() {
        Mono<Asset> result = createAssetsUseCase.createAsset(Mono.empty());

        assertNull(result.block());
    }

    @Test
    void testCreateAsset_repositoryThrowsException() {
        Asset asset = new Asset();
        when(repository.save(asset)).thenThrow(new RuntimeException("Error saving"));

        AssetException exception = assertThrows(AssetException.class, () -> createAssetsUseCase.createAsset(Mono.just(asset)).block());

        assertEquals("Error saving asset: " + Mono.just(asset), exception.getMessage());
    }

    @Test
    void testCreateAsset_handleErrorWithAsset() {
        Asset asset = new Asset();
        when(repository.save(asset)).thenReturn(Mono.error(new RuntimeException("Database error")));

        AssetException exception = assertThrows(AssetException.class, () -> createAssetsUseCase.createAsset(Mono.just(asset)).block());

        assertEquals("Error saving asset: " + Mono.just(asset), exception.getMessage());
    }
}
