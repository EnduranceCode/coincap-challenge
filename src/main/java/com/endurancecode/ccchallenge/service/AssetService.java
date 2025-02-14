/*
 * MIT License
 *
 * Copyright (c) 2025 Ricardo do Canto
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.endurancecode.ccchallenge.service;

import com.endurancecode.ccchallenge.api.dto.AssetDTO;
import com.endurancecode.ccchallenge.api.exception.base.ChallengeException;

import java.math.BigDecimal;

public interface AssetService {

    /**
     * Adds an asset to the user's wallet
     *
     * @param walletId the ID of the wallet
     * @param assetDTO the DTO containing the asset information
     * @throws ChallengeException if there is an error adding the asset
     */
    void save(Long walletId, AssetDTO assetDTO) throws ChallengeException;

    /**
     * Increments the quantity of a specific asset in a wallet.
     *
     * @param walletId          the ID of the wallet containing the asset
     * @param symbol            the symbol of the token to increment
     * @param incrementQuantity the quantity to increment
     * @throws ChallengeException if any error occurs during the operation
     */
    void incrementAssetQuantity(Long walletId, String symbol, BigDecimal incrementQuantity) throws
            ChallengeException;
}
