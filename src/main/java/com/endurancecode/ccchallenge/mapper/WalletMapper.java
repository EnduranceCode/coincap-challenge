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

package com.endurancecode.ccchallenge.mapper;

import com.endurancecode.ccchallenge.api.dto.AssetDTO;
import com.endurancecode.ccchallenge.api.dto.WalletDTO;
import com.endurancecode.ccchallenge.entity.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Mapper class for converting Asset entities to AssetDTOs.
 */
@Component
public class WalletMapper {
    private final AssetMapper assetMapper;

    @Autowired
    public WalletMapper(AssetMapper assetMapper) {
        this.assetMapper = assetMapper;
    }

    /**
     * Converts a Wallet entity to a WalletDTO.
     *
     * @param wallet the Wallet entity to convert
     * @return the converted WalletDTO
     */
    public WalletDTO toDTO(Wallet wallet) {
        WalletDTO walletDTO = new WalletDTO(wallet.getId(), assetMapper.toDTO(wallet.getAssets()));

        BigDecimal total = walletDTO.getAssets()
                .stream()
                .map(AssetDTO::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        walletDTO.setTotal(total);

        return walletDTO;
    }
}

