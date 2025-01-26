package com.inditex.asset.integration;

import com.inditex.asset.AssetApplication;
import com.inditex.asset.domain.Asset;
import com.inditex.asset.domain.repository.AssetRepository;
import com.inditex.asset.infraestructure.request.AssetFileUploadRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

@SpringBootTest(classes= AssetApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("default")
@SuppressWarnings("unchecked")
class AssetControllerIT {
    @Autowired
    private WebTestClient client;
    @Autowired
    private AssetRepository repository;

    @Test
    void uploadAssetFile_202_Scenario() {
        String apiUrl = "/api/mgmt/1/assets/actions/upload";
        AssetFileUploadRequest assetFileUploadRequest = AssetFileUploadRequest.builder().filename("dog.jpg").encodedFile("file3").contentType("jpg").build();

        client.post().uri(apiUrl).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(assetFileUploadRequest))
            .exchange()
            .expectStatus().isAccepted();

    }

    @Test
    void getAssetByFilter_DESC_200_Scenario() {
        String apiUrl = "/api/mgmt/1/assets/";
        Asset expectedAsset = Asset.builder().id("9f42df79-a4cb-415d-b421-6c92c3d79d70").filename("bird.png").contentType("png")
                .url("https://container.inditex.org/file/9f42df79-a4cb-415d-b421-6c92c3d79d70")
                .assetSize(25).uploadDate("2025-01-05T09:00:00").build();
        Asset expectedAsset2 = Asset.builder().id("bcb70f66-6c35-446a-8b7d-7e15707eb612").filename("flower.jpg").contentType("jpg")
                .url("https://container.inditex.org/file/bcb70f66-6c35-446a-8b7d-7e15707eb612").assetSize(5).uploadDate("2024-12-05T09:00:00").build();

        FluxExchangeResult<Mono<ResponseEntity<Flux<Asset>>>> response = client
                .get().uri(uriBuilder ->
                        uriBuilder
                                .path(apiUrl)
                                .queryParam("uploadDateStart", "2024-12-05T09:00:00")
                                .queryParam("uploadDateEnd", "2025-01-06T09:00:00")
                                .queryParam("sortDirection", "DESC")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .returnResult(new ParameterizedTypeReference<>() {
                });

        response.getResponseBody()
                .flatMap(responseEntity -> responseEntity)
                .doOnNext(entity -> StepVerifier.create(Objects.requireNonNull(entity.getBody()))
                        .expectNext(expectedAsset)
                        .expectNext(expectedAsset2)
                        .verifyComplete());
    }

    @Test
    void getAssetByFilter_ASC_200_Scenario() {
        String apiUrl = "/api/mgmt/1/assets/";
        Asset expectedAsset = Asset.builder().id("9f42df79-a4cb-415d-b421-6c92c3d79d70").filename("bird.png").contentType("png")
                .url("https://container.inditex.org/file/9f42df79-a4cb-415d-b421-6c92c3d79d70")
                .assetSize(25).uploadDate("2025-01-05T09:00:00").build();
        Asset expectedAsset2 = Asset.builder().id("bcb70f66-6c35-446a-8b7d-7e15707eb612").filename("flower.jpg").contentType("jpg")
                .url("https://container.inditex.org/file/bcb70f66-6c35-446a-8b7d-7e15707eb612").assetSize(5).uploadDate("2024-12-05T09:00:00").build();

        FluxExchangeResult<Mono<ResponseEntity<Flux<Asset>>>> response = client
                .get().uri( uriBuilder  ->
                        uriBuilder
                                .path(apiUrl)
                                .queryParam("uploadDateStart", "2024-12-05T09:00:00")
                                .queryParam("uploadDateEnd", "2025-01-06T09:00:00")
                                .queryParam("sortDirection", "ASC")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .returnResult(new ParameterizedTypeReference<>() {
                });

        response.getResponseBody()
                .flatMap(responseEntity -> responseEntity)
                .doOnNext(entity -> StepVerifier.create(Objects.requireNonNull(entity.getBody()))
                        .expectNext(expectedAsset2)
                        .expectNext(expectedAsset)
                        .verifyComplete());

    }

    @Test
    void getAssetByFilter_400_Scenario() {
        String apiUrl = "/api/mgmt/1/assets/";

        client
                .get().uri( uriBuilder  ->
                        uriBuilder
                                .path(apiUrl)
                                .queryParam("uploadDateStart", "2024-12-05")
                                .queryParam("uploadDateEnd", "2025-01-06")
                                .queryParam("sortDirection", "wrong")
                                .build())
                .exchange()
                .expectStatus().isBadRequest();

    }

}
