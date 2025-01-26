package com.inditex.asset.intraestructure.api;

import com.inditex.asset.domain.Asset;
import com.inditex.asset.domain.usecase.CreateAssetsUseCase;
import com.inditex.asset.domain.usecase.FindAssetsUseCase;
import com.inditex.asset.infraestructure.api.ApiUtil;
import com.inditex.asset.infraestructure.api.AssetController;
import com.inditex.asset.infraestructure.request.AssetFileUploadRequest;
import com.inditex.asset.infraestructure.request.SortDirection;
import com.inditex.asset.infraestructure.response.AssetFileUploadResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssetControllerTest {

    @Mock
    private CreateAssetsUseCase createAssetsUseCase;

    @Mock
    private FindAssetsUseCase findAssetsUseCase;

    @Mock
    private ApiUtil util;

    @InjectMocks
    private AssetController assetController;

    @Test
    void testGetAssetsByFilter_ValidSortDirection() {
        String uploadDateStart = "2023-01-01";
        String uploadDateEnd = "2023-01-31";
        String filename = "testFile";
        String filetype = "image/png";
        String sortDirection = "ASC";

        Asset asset = new Asset();
        Flux<Asset> assetFlux = Flux.just(asset);

        when(findAssetsUseCase.getAssetByFilter(uploadDateStart, uploadDateEnd, filename, filetype, SortDirection.ASC))
                .thenReturn(assetFlux);

        Mono<ResponseEntity<Flux<Asset>>> result = assetController.getAssetsByFilter(uploadDateStart, uploadDateEnd, filename, filetype, sortDirection);

        ResponseEntity<Flux<Asset>> response = result.block();
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().count().block());
    }

    @Test
    void testGetAssetsByFilter_InvalidSortDirection() {
        String sortDirection = "INVALID";

        Mono<ResponseEntity<Flux<Asset>>> result = assetController.getAssetsByFilter("2023-01-01", "2023-01-31", "testFile", "image/png", sortDirection);

        ResponseEntity<Flux<Asset>> response = result.block();
        assertNotNull(response);
        assertEquals(0, response.getBody().count().block());
    }

    @Test
    void testUploadAssetFile_Success() {
        AssetFileUploadRequest request = new AssetFileUploadRequest();
        Asset asset = Asset.builder().id("1").build();
        when(util.mapAssetFileUploadRequest(any(Mono.class))).thenReturn(Mono.just(asset));
        when(createAssetsUseCase.createAsset(any(Mono.class))).thenReturn(Mono.just(asset));

        Mono<ResponseEntity<AssetFileUploadResponse>> result = assetController.uploadAssetFile(Mono.just(request));

        ResponseEntity<AssetFileUploadResponse> response = result.block();
        assertNotNull(response);
        assertEquals(202, response.getStatusCodeValue());
        assertEquals("1", response.getBody().getId());
    }

    @Test
    void testUploadAssetFile_CreateAssetFailure() {
        AssetFileUploadRequest request = new AssetFileUploadRequest();
        Asset asset = Asset.builder().id("1").build();
        when(util.mapAssetFileUploadRequest(any(Mono.class))).thenReturn(Mono.just(asset));
        when(createAssetsUseCase.createAsset(any(Mono.class))).thenReturn(Mono.empty());

        Mono<ResponseEntity<AssetFileUploadResponse>> result = assetController.uploadAssetFile(Mono.just(request));

        assertThrows(RuntimeException.class, result::block);
    }
}
