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

import com.endurancecode.ccchallenge.integration.coincap.dto.CoinCapAssetDTO;
import com.endurancecode.ccchallenge.integration.coincap.service.CoinCapAssetService;
import com.endurancecode.ccchallenge.entity.Token;
import com.endurancecode.ccchallenge.repository.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TokenServiceImplTest {
    @InjectMocks
    private TokenServiceImpl underTest;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private CoinCapAssetService coinCapAssetService;

    private ExecutorService executorService;

    @BeforeEach
    void setUp() {
        executorService = Executors.newFixedThreadPool(3);
    }

    @Test
    void updateTokenPrices() throws InterruptedException {
        Token bdToken1 = new Token("BTC", new BigDecimal("100000.00"));
        Token bdToken2 = new Token("ETH", new BigDecimal("4000.00"));
        List<Token> bdTokens = List.of(bdToken1, bdToken2);

        CoinCapAssetDTO coinCapAsset1 = new CoinCapAssetDTO("BTC", "100100.00");
        CoinCapAssetDTO coinCapAsset2 = new CoinCapAssetDTO("ETH", "4100.00");
        Set<CoinCapAssetDTO> coinCapAssets = Set.of(coinCapAsset1, coinCapAsset2);

        when(tokenRepository.findAll()).thenReturn(bdTokens);
        when(coinCapAssetService.fetchAllAssets()).thenReturn(coinCapAssets);

        underTest.updateTokenPrices();

        executorService.shutdown();
        if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
            fail("Executor did not terminate in the specified time.");
        }

        verify(tokenRepository, times(1)).findAll();
        verify(coinCapAssetService, times(1)).fetchAllAssets();
        assertEquals(new BigDecimal("100100.00"), bdToken1.getPrice());
        assertEquals(new BigDecimal("4100.00"), bdToken2.getPrice());
    }
}
