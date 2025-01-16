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

import com.endurancecode.ccchallenge.api.dto.WalletEvaluationInputDTO;
import com.endurancecode.ccchallenge.api.dto.WalletEvaluationOutputDTO;
import com.endurancecode.ccchallenge.integration.coincap.dto.CoinCapAssetDTO;
import com.endurancecode.ccchallenge.integration.coincap.service.CoinCapAssetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class WalletEvaluationServiceImplTest {

    @InjectMocks
    WalletEvaluationServiceImpl underTest;

    @Mock
    CoinCapAssetService coinCapAssetService;

    @Test
    void evaluate() {
        List<WalletEvaluationInputDTO> tokens = getTestInput();
        Set<CoinCapAssetDTO> tokensAPI = getTestCoinCapData();

        when(coinCapAssetService.fetchAllAssets()).thenReturn(tokensAPI);

        WalletEvaluationOutputDTO evaluation = underTest.evaluate(tokens);

        verify(coinCapAssetService, times(1)).fetchAllAssets();

        assertEquals("XRP", evaluation.getBestAsset());
        assertEquals("SOL", evaluation.getWorstAsset());
        assertEquals(11.11D, evaluation.getBestPerformance());
        assertEquals(-11.11D, evaluation.getWorstPerformance());
        assertEquals(new BigDecimal("988.89"), evaluation.getTotal());
    }

    private static List<WalletEvaluationInputDTO> getTestInput() {
        WalletEvaluationInputDTO btc = new WalletEvaluationInputDTO(
                "BTC", new BigDecimal("1.0"), new BigDecimal("100.00"));
        WalletEvaluationInputDTO eth = new WalletEvaluationInputDTO(
                "ETH", new BigDecimal("1.0"), new BigDecimal("200.00"));
        WalletEvaluationInputDTO xrp = new WalletEvaluationInputDTO(
                "XRP", new BigDecimal("1.0"), new BigDecimal("300.00"));
        WalletEvaluationInputDTO sol = new WalletEvaluationInputDTO(
                "SOL", new BigDecimal("1.0"), new BigDecimal("400.00"));
        return List.of(btc, eth, xrp, sol);
    }

    private static Set<CoinCapAssetDTO> getTestCoinCapData() {
        CoinCapAssetDTO btcAPI = new CoinCapAssetDTO("BTC", "110.00");
        CoinCapAssetDTO ethAPI = new CoinCapAssetDTO("ETH", "190.00");
        CoinCapAssetDTO xrpAPI = new CoinCapAssetDTO("XRP", "333.33");
        CoinCapAssetDTO solAPI = new CoinCapAssetDTO("SOL", "355.56");
        return Set.of(btcAPI, ethAPI, xrpAPI, solAPI);
    }
}
