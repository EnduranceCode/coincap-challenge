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
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Mapper class for converting Asset entities to AssetDTOs.
 */
@Component
public class AssetMapper {

    /**
     * Converts a list of Asset entities to a list of AssetDTOs.
     *
     * @param assets the list of Asset entities to convert
     * @return the list of converted AssetDTOs
     */
    public List<AssetDTO> toDTO(List<Asset> assets) {
        return assets.stream().map(this::toDTO).toList();
    }

    /**
     * Converts a single Asset entity to an AssetDTO.
     *
     * @param asset the Asset entity to convert
     * @return the converted AssetDTO
     */
    public AssetDTO toDTO(Asset asset) {
        BigDecimal price = asset.getToken().getPrice();
        BigDecimal quantity = asset.getQuantity();
        BigDecimal value = quantity.multiply(price);

        AssetDTO assetDTO = new AssetDTO(asset.getToken().getSymbol(), quantity);
        assetDTO.setId(asset.getId());
        assetDTO.setPrice(price);
        assetDTO.setValue(value);

        return assetDTO;
    }
}
