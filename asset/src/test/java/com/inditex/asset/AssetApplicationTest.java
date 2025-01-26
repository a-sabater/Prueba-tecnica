package com.inditex.asset;

import com.inditex.asset.infraestructure.api.AssetController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("default")
@SpringBootTest(classes = {AssetApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AssetApplicationTest {

	@Autowired
	private AssetController assetController;

	@Test
	void contextLoads() {
		assertThat(assetController).isNotNull();
	}

}
