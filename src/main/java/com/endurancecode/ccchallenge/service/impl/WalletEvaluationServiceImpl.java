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
import com.endurancecode.ccchallenge.service.WalletEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;

@Service
public class WalletEvaluationServiceImpl implements WalletEvaluationService {
    private final CoinCapAssetService coinCapAssetService;

    @Autowired
    public WalletEvaluationServiceImpl(CoinCapAssetService coinCapAssetService) {
        this.coinCapAssetService = coinCapAssetService;
    }

    @Override
    public WalletEvaluationOutputDTO evaluate(List<WalletEvaluationInputDTO> tokens) {
        Set<CoinCapAssetDTO> coinCapAssets = coinCapAssetService.fetchAllAssets();
        WalletEvaluationOutputDTO evaluation = new WalletEvaluationOutputDTO();
        evaluation.setTotal(new BigDecimal(0));

        for (WalletEvaluationInputDTO token : tokens) {
            CoinCapAssetDTO asset = coinCapAssets.stream()
                    .filter(dto -> dto.getSymbol().equals(token.getSymbol()))
                    .findFirst()
                    .orElse(null);

            if (asset != null && asset.getPriceUsd() != null) {
                BigDecimal currentValue = new BigDecimal(asset.getPriceUsd());
                evaluation.setTotal(evaluation.getTotal().add(currentValue));

                double performance = currentValue.divide(token.getAverageBuyValue(), 10, RoundingMode.HALF_UP)
                        .subtract(BigDecimal.ONE)
                        .multiply(new BigDecimal("100"))
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue();

                if (evaluation.getBestPerformance() == null || performance > evaluation.getBestPerformance()) {
                    evaluation.setBestAsset(token.getSymbol());
                    evaluation.setBestPerformance(performance);
                }

                if (evaluation.getWorstPerformance() == null || performance < evaluation.getWorstPerformance()) {
                    evaluation.setWorstAsset(token.getSymbol());
                    evaluation.setWorstPerformance(performance);
                }
            }
        }

        return evaluation;
    }
}
