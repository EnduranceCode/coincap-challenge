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

package com.endurancecode.ccchallenge.integration.coincap.service.impl;


import com.endurancecode.ccchallenge.integration.coincap.dto.CoinCapAssetDTO;
import com.endurancecode.ccchallenge.integration.coincap.dto.CoinCapAssetResponse;
import com.endurancecode.ccchallenge.integration.coincap.service.CoinCapAssetService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CoinCapAssetServiceImpl implements CoinCapAssetService {
    private final WebClient webClient;

    public CoinCapAssetServiceImpl(WebClient coinCapWebClient) {
        this.webClient = coinCapWebClient;
    }

    @Override
    public Set<CoinCapAssetDTO> fetchAllAssets() {
        int limit = 500;
        final AtomicInteger offset = new AtomicInteger(0);
        Set<CoinCapAssetDTO> allAssets = new HashSet<>();

        while (true) {
            CoinCapAssetResponse response = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/assets")
                            .queryParam("limit", limit)
                            .queryParam("offset", offset.get())
                            .build())
                    .retrieve()
                    .bodyToMono(CoinCapAssetResponse.class)
                    .block();

            if (response == null || response.getData().isEmpty()) {
                return allAssets;
            } else {
                allAssets.addAll(response.getData());

                if (response.getData().size() < limit) {
                    return allAssets;
                }

                offset.addAndGet(limit);
            }
        }
    }
}