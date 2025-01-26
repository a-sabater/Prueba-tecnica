package com.inditex.asset.application.usecase;

import com.inditex.asset.domain.Asset;
import com.inditex.asset.domain.AssetException;
import com.inditex.asset.domain.repository.AssetRepository;
import com.inditex.asset.infraestructure.request.SortDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAssetsUseCaseImplTest {

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private FindAssetsUseCaseImpl findAssetsUseCase;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testGetAssetByFilterAsc() {
        String uploadDateStart = "2022-01-01T08:00:00";
        String uploadDateEnd = "2022-01-31T08:00:00";
        String filename = "testFile.jpg";
        String filetype = "txt";

        Asset asset1 = new Asset();
        asset1.setFilename("testFile1.jpg");
        Asset asset2 = new Asset();
        asset2.setFilename("testFile2.jpg");

        Flux<Asset> assets = Flux.just(asset1, asset2);
        when(assetRepository.findAllByUploadDateAsc(uploadDateStart, uploadDateEnd)).thenReturn(Optional.of(assets));

        Flux<Asset> result = findAssetsUseCase.getAssetByFilter(uploadDateStart, uploadDateEnd, filename, filetype, SortDirection.ASC);

        assertEquals(2, result.count().block());
        verify(assetRepository).findAllByUploadDateAsc(uploadDateStart, uploadDateEnd);
    }

    @Test
    void testGetAssetByFilterDesc() {
        String uploadDateStart = "2022-01-01T08:00:00";
        String uploadDateEnd = "2022-01-31T08:00:00";
        String filename = "testFile.jpg";
        String filetype = "txt";

        Asset asset1 = new Asset();
        asset1.setFilename("testFile1.jpg");
        Asset asset2 = new Asset();
        asset2.setFilename("testFile2.jpg");

        Flux<Asset> assets = Flux.just(asset1, asset2);
        when(assetRepository.findAllByUploadDateDesc(uploadDateStart, uploadDateEnd)).thenReturn(Optional.of(assets));

        Flux<Asset> result = findAssetsUseCase.getAssetByFilter(uploadDateStart, uploadDateEnd, filename, filetype, SortDirection.DESC);

        assertEquals(2, result.count().block());
        verify(assetRepository).findAllByUploadDateDesc(uploadDateStart, uploadDateEnd);
    }

    @Test
    void testGetAssetByFilterAscThrowsException() {
        String uploadDateStart = "2022-01-01T08:00:00";
        String uploadDateEnd = "2022-01-31T08:00:00";

        assertThrows(AssetException.class, () -> findAssetsUseCase.getAssetByFilter(uploadDateStart, uploadDateEnd, null, null, SortDirection.ASC));
    }

    @Test
    void testGetAssetByFilterDescThrowsException() {
        String uploadDateStart = "2022-01-01T08:00:00";
        String uploadDateEnd = "2022-01-31T08:00:00";

        when(assetRepository.findAllByUploadDateDesc(uploadDateStart, uploadDateEnd)).thenReturn(Optional.empty());

        assertThrows(AssetException.class, () -> {
            findAssetsUseCase.getAssetByFilter(uploadDateStart, uploadDateEnd, null, null, SortDirection.DESC);
        });
    }

    @Test
    void testGetAssetByFilterWithEmptyParameters() {
        String uploadDateStart = "";
        String uploadDateEnd = "";

        Asset asset1 = new Asset();
        asset1.setFilename("testFile1.jpg");

        Flux<Asset> assets = Flux.just(asset1);
        when(assetRepository.findAllByUploadDateAsc(uploadDateStart, uploadDateEnd)).thenReturn(Optional.of(assets));

        Flux<Asset> result = findAssetsUseCase.getAssetByFilter(uploadDateStart, uploadDateEnd, null, null, SortDirection.ASC);

        assertEquals(1, result.count().block());
    }
}
