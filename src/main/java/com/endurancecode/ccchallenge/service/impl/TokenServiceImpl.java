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
import com.endurancecode.ccchallenge.service.TokenService;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Service
public class TokenServiceImpl implements TokenService {
    private static final Logger LOGGER = Logger.getLogger(TokenServiceImpl.class.getName());

    private final TokenRepository tokenRepository;
    private final CoinCapAssetService coinCapAssetService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    @Autowired
    public TokenServiceImpl(TokenRepository tokenRepository, CoinCapAssetService coinCapAssetService) {
        this.tokenRepository = tokenRepository;
        this.coinCapAssetService = coinCapAssetService;
    }

    @Override
    @Scheduled(fixedRateString = "${app.scheduler.token.update-interval}")
    public void updateTokenPrices() {
        LOGGER.info("START : Updating token prices");

        List<Token> tokens = tokenRepository.findAll();
        Set<CoinCapAssetDTO> coinCapAssets = coinCapAssetService.fetchAllAssets();

        for (int i = 0; i < tokens.size(); i += 3) {
            List<Token> tokenBatch = tokens.subList(i, Math.min(i + 3, tokens.size()));

            executorService.submit(() -> updatePricesForBatch(tokenBatch, coinCapAssets));
        }

        LOGGER.info("END : Updating token prices");
    }

    @PreDestroy
    public void shutDownExecutorService() {
        LOGGER.info("Shutting down executor service...");
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                LOGGER.warning("Executor did not terminate in the specified time. Forcing shutdown...");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            LOGGER.severe("Thread interrupted during executor service shutdown. Restoring interrupt status...");
            Thread.currentThread().interrupt();
            executorService.shutdownNow();
        }
    }

    @Transactional
    protected void updatePricesForBatch(List<Token> tokenBatch, Set<CoinCapAssetDTO> coinCapAssets) {
        for (Token token : tokenBatch) {
            coinCapAssets.stream()
                    .filter(asset -> token.getSymbol().equals(asset.getSymbol()))
                    .findFirst()
                    .ifPresent(asset -> token.setPrice(new BigDecimal(asset.getPriceUsd())));
        }
        tokenRepository.saveAll(tokenBatch);
    }
}
