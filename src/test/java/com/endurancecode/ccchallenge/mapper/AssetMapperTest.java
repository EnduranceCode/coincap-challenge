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
import com.endurancecode.ccchallenge.entity.Asset;
import com.endurancecode.ccchallenge.entity.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class AssetMapperTest {

    @InjectMocks
    private AssetMapper underTest;

    Token token;
    Asset asset;

    @BeforeEach
    void setUp() {
        token = new Token("BTC", BigDecimal.valueOf(30000.00));
        asset = new Asset(null, token, BigDecimal.valueOf(0.5));
        asset.setId(1L);
    }

    @Test
    void testEntityToDTO() {
        AssetDTO assetDTO = underTest.toDTO(asset);

        assertEquals(1L, assetDTO.getId());
        assertEquals("BTC", assetDTO.getSymbol());
        assertEquals(BigDecimal.valueOf(0.5), assetDTO.getQuantity());
        assertEquals(BigDecimal.valueOf(30000.00), assetDTO.getPrice());
        assertEquals(0, BigDecimal.valueOf(15000.00).compareTo(assetDTO.getValue()));
    }

    @Test
    void testListToDTO() {
        List<AssetDTO> assetsDTO = underTest.toDTO(List.of(asset));

        assertEquals(1L, assetsDTO.get(0).getId());
        assertEquals("BTC", assetsDTO.get(0).getSymbol());
        assertEquals(BigDecimal.valueOf(0.5), assetsDTO.get(0).getQuantity());
        assertEquals(BigDecimal.valueOf(30000.00), assetsDTO.get(0).getPrice());
        assertEquals(0, BigDecimal.valueOf(15000.00).compareTo(assetsDTO.get(0).getValue()));
    }
}
