package com.inditex.asset.domain.usecase;

import com.inditex.asset.domain.Asset;
import com.inditex.asset.infraestructure.request.SortDirection;
import reactor.core.publisher.Flux;

/**
 * The interface Find assets use case.
 */
public interface FindAssetsUseCase {

	/**
	 * Get flux of Asset by a given filter
	 *
	 * @param uploadDateStart the upload date start
	 * @param uploadDateEnd   the upload date end
	 * @param filename        the filename
	 * @param filetype        the filetype
	 * @param sortDirection   the sortDirection
	 * @return Flux<Asset> asset by filter
	 */
	Flux<Asset> getAssetByFilter(String uploadDateStart, String uploadDateEnd, String filename, String filetype,
			SortDirection sortDirection);

}
