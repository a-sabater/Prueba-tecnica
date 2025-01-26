package com.inditex.asset.domain;

import lombok.Getter;

/**
 * Custom exception
 */
@Getter
public class AssetException extends RuntimeException {

    /**
     * Instantiates a new Asset exception.
     *
     * @param message the message
     */
    public AssetException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new Asset exception.
     *
     * @param message the message
     * @param err     the err
     */
    public AssetException(String message, Throwable err){
        super(message, err);
    }

}
