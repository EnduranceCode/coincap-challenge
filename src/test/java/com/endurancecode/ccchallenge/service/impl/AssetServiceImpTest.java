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
import com.endurancecode.ccchallenge.api.exception.PersistenceException;
import com.endurancecode.ccchallenge.api.exception.WalletNotFoundException;
import com.endurancecode.ccchallenge.api.exception.base.ChallengeException;
import com.endurancecode.ccchallenge.entity.Asset;
import com.endurancecode.ccchallenge.entity.Token;
import com.endurancecode.ccchallenge.entity.Wallet;
import com.endurancecode.ccchallenge.mapper.AssetMapper;
import com.endurancecode.ccchallenge.repository.AssetRepository;
import com.endurancecode.ccchallenge.repository.TokenRepository;
import com.endurancecode.ccchallenge.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AssetServiceImpTest {

    @InjectMocks
    AssetServiceImp underTest;

    @Mock
    AssetMapper assetMapper;

    @Mock
    WalletRepository walletRepository;

    @Mock
    AssetRepository assetRepository;

    @Mock
    TokenRepository tokenRepository;

    Long walletId;
    Wallet wallet;
    Asset asset;
    AssetDTO assetErrorDTO;
    Token tokenError;

    @BeforeEach
    void setUp() {
        walletId = 1L;
        wallet = new Wallet();
        wallet.setId(walletId);
        asset = new Asset();
        asset.setQuantity(new BigDecimal("1.00"));
        tokenError = new Token("BTC", new BigDecimal("100000.00"));
        asset.setToken(tokenError);
        wallet.setAssets(List.of(asset));
        assetErrorDTO = new AssetDTO("BTC", new BigDecimal("100000.00"));
    }

    @Test
    void saveErrorWalletNotFound() {
        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

        WalletNotFoundException exception = assertThrows(
                WalletNotFoundException.class,
                () -> underTest.save(walletId, assetErrorDTO)
        );

        assertEquals("Wallet with id 1 was not found in the database", exception.getMessage());
        verify(walletRepository, times(1)).findById(walletId);
        verifyNoInteractions(tokenRepository, assetRepository, assetMapper);
    }

    @Test
    void saveErrorAssetAlreadyExists() {
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        PersistenceException exception = assertThrows(
                PersistenceException.class,
                () -> underTest.save(walletId, assetErrorDTO)
        );

        assertEquals("Asset with symbol BTC already exists in the Wallet with id 1", exception.getMessage());
        verify(walletRepository, times(1)).findById(walletId);
        verifyNoInteractions(tokenRepository, assetRepository, assetMapper);
    }

    @Test
    void save() throws ChallengeException {
        AssetDTO assetSuccessDTO = new AssetDTO("ETH", new BigDecimal("1.00"));
        Asset assetSuccess = new Asset();
        assetSuccess.setQuantity(new BigDecimal("1.00"));
        Token tokenSuccess = new Token("ETH", new BigDecimal("100000.00"));

        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        when(tokenRepository.findBySymbol("ETH")).thenReturn(Optional.of(tokenSuccess));
        when(assetMapper.toEntity(assetSuccessDTO)).thenReturn(assetSuccess);

        underTest.save(walletId, assetSuccessDTO);

        verify(walletRepository, times(1)).findById(walletId);
        verify(tokenRepository, times(1)).findBySymbol("ETH");
    }

    @Test
    void incrementAssetQuantitySuccess() throws ChallengeException {
        when(assetRepository.findByWalletIdAndTokenSymbol(1L, "BTC")).thenReturn(Optional.of(asset));

        underTest.incrementAssetQuantity(1L, "BTC", new BigDecimal("1.00"));

        verify(assetRepository, times(1)).findByWalletIdAndTokenSymbol(1L, "BTC");
    }

    @Test
    void incrementAssetQuantityError() {
        when(assetRepository.findByWalletIdAndTokenSymbol(1L, "BTC")).thenReturn(Optional.empty());

        assertThrows(
                ChallengeException.class, () -> underTest.incrementAssetQuantity(1L, "BTC", new BigDecimal("1.00")));

        verify(assetRepository, times(1)).findByWalletIdAndTokenSymbol(1L, "BTC");
    }
}