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

import com.endurancecode.ccchallenge.api.dto.AssetDTO;
import com.endurancecode.ccchallenge.api.dto.ErrorDTO;
import com.endurancecode.ccchallenge.api.exception.AssetNotFoundException;
import com.endurancecode.ccchallenge.api.exception.PersistenceException;
import com.endurancecode.ccchallenge.api.exception.WalletNotFoundException;
import com.endurancecode.ccchallenge.api.exception.base.ChallengeError;
import com.endurancecode.ccchallenge.api.exception.base.ChallengeException;
import com.endurancecode.ccchallenge.api.exception.base.DatabaseException;
import com.endurancecode.ccchallenge.entity.Asset;
import com.endurancecode.ccchallenge.entity.Token;
import com.endurancecode.ccchallenge.entity.Wallet;
import com.endurancecode.ccchallenge.integration.coincap.dto.CoinCapAssetDTO;
import com.endurancecode.ccchallenge.integration.coincap.service.CoinCapAssetService;
import com.endurancecode.ccchallenge.mapper.AssetMapper;
import com.endurancecode.ccchallenge.repository.AssetRepository;
import com.endurancecode.ccchallenge.repository.TokenRepository;
import com.endurancecode.ccchallenge.repository.WalletRepository;
import com.endurancecode.ccchallenge.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.logging.Logger;

import static java.lang.String.format;

@Service
public class AssetServiceImp implements AssetService {
    private static final Logger LOGGER = Logger.getLogger(AssetServiceImp.class.getName());

    private final CoinCapAssetService coinCapAssetService;
    private final WalletRepository walletRepository;
    private final AssetRepository assetRepository;
    private final TokenRepository tokenRepository;
    private final AssetMapper assetMapper;

    @Autowired
    public AssetServiceImp(
            CoinCapAssetService coinCapAssetService, WalletRepository walletRepository, AssetRepository assetRepository,
            TokenRepository tokenRepository, AssetMapper assetMapper
    ) {
        this.coinCapAssetService = coinCapAssetService;
        this.walletRepository = walletRepository;
        this.assetRepository = assetRepository;
        this.tokenRepository = tokenRepository;
        this.assetMapper = assetMapper;
    }

    @Override
    @Transactional
    public void save(Long walletId, AssetDTO assetDTO) throws ChallengeException {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> {
            String message = format("Wallet with id %s was not found in the database", walletId);
            LOGGER.warning(message);
            return new WalletNotFoundException(message, new ErrorDTO(ChallengeError.WALLET_NOT_FOUND));
        });

        boolean walletHasAsset = wallet.getAssets()
                .stream()
                .anyMatch(asset -> asset.getToken().getSymbol().equals(assetDTO.getSymbol()));
        if (walletHasAsset) {
            String message = format(
                    "Asset with symbol %s already exists in the Wallet with id %d",
                    assetDTO.getSymbol(), walletId
            );
            LOGGER.warning(message);
            throw new PersistenceException(message, new ErrorDTO(ChallengeError.ASSET_ALREADY_EXISTS));
        }

        Asset asset = assetMapper.toEntity(assetDTO);
        asset.setWallet(wallet);

        Optional<Token> dbToken = tokenRepository.findBySymbol(assetDTO.getSymbol());

        Token token;
        if(dbToken.isPresent()) {
            token = dbToken.get();
        } else {
            CoinCapAssetDTO coinCapAssetDTO = coinCapAssetService.fetchAssetBySymbol(assetDTO.getSymbol());
            token = new Token(assetDTO.getSymbol(),new BigDecimal(coinCapAssetDTO.getPriceUsd()));

            try {
                tokenRepository.save(token);
            } catch (Exception e) {
                String message = e.getMessage();
                LOGGER.severe(message);
                throw new DatabaseException(message, new ErrorDTO(ChallengeError.PERSISTENCE_ERROR));
            }
        }
        asset.setToken(token);

        try {
            assetRepository.save(asset);
        } catch (Exception e) {
            String message = e.getMessage();
            LOGGER.severe(message);
            throw new DatabaseException(message, new ErrorDTO(ChallengeError.PERSISTENCE_ERROR));
        }
    }

    @Override
    @Transactional
    public void incrementAssetQuantity(Long walletId, String symbol, BigDecimal incrementQuantity) throws
            ChallengeException {

        Asset asset = assetRepository.findByWalletIdAndTokenSymbol(walletId, symbol)
                .orElseThrow(() -> {
                    String message = format(
                            "Asset with symbol %s wasn't found in the Wallet with id %d", symbol, walletId);
                    LOGGER.warning(message);
                    return new AssetNotFoundException(message, new ErrorDTO(ChallengeError.ASSET_NOT_FOUND_DB));
                });

        asset.setQuantity(asset.getQuantity().add(incrementQuantity));
        String message = format("Asset with symbol %s incremented by %s", symbol, incrementQuantity);
        LOGGER.info(message);
    }
}
