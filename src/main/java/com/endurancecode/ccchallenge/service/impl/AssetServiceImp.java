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

package com.endurancecode.ccchallenge.service.impl;

import com.endurancecode.ccchallenge.api.dto.ErrorDTO;
import com.endurancecode.ccchallenge.api.exception.AssetNotFoundException;
import com.endurancecode.ccchallenge.api.exception.base.ChallengeError;
import com.endurancecode.ccchallenge.api.exception.base.ChallengeException;
import com.endurancecode.ccchallenge.entity.Asset;
import com.endurancecode.ccchallenge.repository.AssetRepository;
import com.endurancecode.ccchallenge.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.logging.Logger;

import static java.lang.String.format;

@Service
public class AssetServiceImp implements AssetService {
    private static final Logger LOGGER = Logger.getLogger(AssetServiceImp.class.getName());

    private final AssetRepository assetRepository;

    @Autowired
    public AssetServiceImp(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Override
    @Transactional
    public void incrementAssetQuantity(Long walletId, String tokenSymbol, BigDecimal incrementQuantity) throws
            ChallengeException {

        Asset asset = assetRepository.findByWalletIdAndTokenSymbol(walletId, tokenSymbol)
                .orElseThrow(() -> {
                    String message = format("Asset with symbol %s wasn't found in the Wallet with id %d", tokenSymbol, walletId);
                    LOGGER.warning(message);
                    return new AssetNotFoundException(message, new ErrorDTO(ChallengeError.ASSET_NOT_FOUND));
                });

        asset.setQuantity(asset.getQuantity().add(incrementQuantity));
        String message = format("Asset with symbol %s incremented by %s", tokenSymbol, incrementQuantity);
        LOGGER.info(message);
    }
}
