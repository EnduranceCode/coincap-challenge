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

import com.endurancecode.ccchallenge.api.exception.base.ChallengeException;
import com.endurancecode.ccchallenge.entity.Asset;
import com.endurancecode.ccchallenge.entity.Token;
import com.endurancecode.ccchallenge.entity.Wallet;
import com.endurancecode.ccchallenge.repository.AssetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AssetServiceImpTest {

    @InjectMocks
    AssetServiceImp underTest;

    @Mock
    AssetRepository assetRepository;

    @Test
    void incrementAssetQuantitySuccess() throws ChallengeException {
        Token token = new Token("BTC", new BigDecimal("100000.00"));
        Asset asset = new Asset(new Wallet(), token, new BigDecimal("1.00"));

        when(assetRepository.findByWalletIdAndTokenSymbol(1L, "BTC")).thenReturn(Optional.of(asset));

        underTest.incrementAssetQuantity(1L, "BTC", new BigDecimal("1.00"));

        verify(assetRepository, times(1)).findByWalletIdAndTokenSymbol(1L, "BTC");
    }

    @Test
    void incrementAssetQuantityError() {
        when(assetRepository.findByWalletIdAndTokenSymbol(1L, "BTC")).thenReturn(Optional.empty());

        assertThrows(ChallengeException.class, () -> underTest.incrementAssetQuantity(1L, "BTC", new BigDecimal("1.00")));

        verify(assetRepository, times(1)).findByWalletIdAndTokenSymbol(1L, "BTC");
    }
}