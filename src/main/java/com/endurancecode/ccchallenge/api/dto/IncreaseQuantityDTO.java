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

package com.endurancecode.ccchallenge.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Schema(description = "Data Transfer Object that defines the Asset's quantity to increase")
public class IncreaseQuantityDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Asset's quantity to increase", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal increaseQuantity;

    public IncreaseQuantityDTO() {
        super();
    }

    public IncreaseQuantityDTO(BigDecimal increaseQuantity) {
        this.increaseQuantity = increaseQuantity;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("increaseQuantity", increaseQuantity)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        IncreaseQuantityDTO that = (IncreaseQuantityDTO) o;

        return new EqualsBuilder().append(increaseQuantity, that.increaseQuantity)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(increaseQuantity).toHashCode();
    }

    public BigDecimal getIncreaseQuantity() {
        return increaseQuantity;
    }

    public void setIncreaseQuantity(BigDecimal increaseQuantity) {
        this.increaseQuantity = increaseQuantity;
    }
}
