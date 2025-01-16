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

@Schema(description = "Data Transfer Object representing an asset of the wallet to be evaluated")
public class WalletEvaluationInputDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(
            description = "Symbol of the asset, e.g., BTC, ETH",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "BTC"
    )
    String symbol;

    @Schema(
            description = "Quantity of the token in the wallet",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "0.12345"
    )
    BigDecimal quantity;

    @Schema(
            description = "Average buy value of the token",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "37870.5058"
    )
    BigDecimal averageBuyValue;

    public WalletEvaluationInputDTO() {
        super();
    }

    public WalletEvaluationInputDTO(String symbol, BigDecimal quantity, BigDecimal averageBuyValue) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.averageBuyValue = averageBuyValue;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("symbol", symbol)
                .append("quantity", quantity)
                .append("averageBuyValue", averageBuyValue)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WalletEvaluationInputDTO that = (WalletEvaluationInputDTO) o;

        return new EqualsBuilder().append(symbol, that.symbol)
                .append(quantity, that.quantity)
                .append(averageBuyValue, that.averageBuyValue)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(symbol).append(quantity).append(averageBuyValue).toHashCode();
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAverageBuyValue() {
        return averageBuyValue;
    }

    public void setAverageBuyValue(BigDecimal averageBuyValue) {
        this.averageBuyValue = averageBuyValue;
    }
}
