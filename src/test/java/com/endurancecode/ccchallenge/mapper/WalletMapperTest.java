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
import com.endurancecode.ccchallenge.entity.Asset;
import com.endurancecode.ccchallenge.entity.Token;
import com.endurancecode.ccchallenge.entity.Wallet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class WalletMapperTest {
    @InjectMocks
    private WalletMapper underTest;

    @Mock
    private AssetMapper assetMapper;

    @Test
    void testToDTO() {
        Token token = new Token("BTC", BigDecimal.valueOf(30000.00));
        Asset asset = new Asset(null, token, BigDecimal.valueOf(0.5));
        asset.setId(1L);

        AssetDTO assetDTO = new AssetDTO();
        assetDTO.setId(1L);
        assetDTO.setSymbol("BTC");
        assetDTO.setQuantity(BigDecimal.valueOf(0.5));
        assetDTO.setPrice(BigDecimal.valueOf(30000.00));
        assetDTO.setValue(BigDecimal.valueOf(15000.00));

        Wallet wallet = new Wallet();
        wallet.setId(1L);
        wallet.setAssets(List.of(asset));

        when(assetMapper.toDTO(List.of(asset))).thenReturn(List.of(assetDTO));

        WalletDTO walletDTO = underTest.toDTO(wallet);

        assertEquals(1L, walletDTO.getId());
        assertEquals(BigDecimal.valueOf(15000.00), walletDTO.getTotal());
        assertEquals(1, walletDTO.getAssets().size());
    }
}
