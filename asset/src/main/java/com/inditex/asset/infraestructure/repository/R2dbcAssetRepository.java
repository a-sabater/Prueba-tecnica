package com.inditex.asset.infraestructure.repository;

import com.inditex.asset.infraestructure.entity.AssetEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.OffsetDateTime;

/**
 * The interface R 2 dbc asset repository.
 */
@Repository
public interface R2dbcAssetRepository extends R2dbcRepository<AssetEntity, String>{

    /**
     * Finds all the AssetEntity whose uploadDate value is between the uploadDateStart and uploadDateEnd, in descending order
     *
     * @param uploadDateStart the upload date start
     * @param uploadDateEnd   the upload date end
     * @return Flux<AssetEntity> flux
     */
    @Query(value = "SELECT ID, FILENAME, CONTENT_TYPE, URL, ASSET_SIZE, UPLOAD_DATE FROM ASSET WHERE UPLOAD_DATE >= :uploadDateStart AND UPLOAD_DATE <= :uploadDateEnd ORDER BY UPLOAD_DATE DESC")
    Flux<AssetEntity> findAllByDateDesc(OffsetDateTime uploadDateStart, OffsetDateTime uploadDateEnd);

    /**
     * Finds all the AssetEntity whose uploadDate value is between the uploadDateStart and uploadDateEnd, in ascending order
     *
     * @param uploadDateStart the upload date start
     * @param uploadDateEnd   the upload date end
     * @return Flux<AssetEntity> flux
     */
    @Query(value = "SELECT ID, FILENAME, CONTENT_TYPE, URL, ASSET_SIZE, UPLOAD_DATE FROM ASSET WHERE UPLOAD_DATE >= :uploadDateStart AND UPLOAD_DATE <= :uploadDateEnd ORDER BY UPLOAD_DATE ASC")
    Flux<AssetEntity> findAllByDateAsc(OffsetDateTime uploadDateStart, OffsetDateTime uploadDateEnd);
}
