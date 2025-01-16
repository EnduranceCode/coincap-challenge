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

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public class WalletEvaluationOutputDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Total value of the wallet", example = "500000.00")
    BigDecimal total;

    @JsonProperty("best_asset")
    @Schema(description = "The token that made the most money in percentage", example = "BTC")
    String bestAsset;

    @JsonProperty("best_performance")
    @Schema(description = "The price variation, in percentage, of the best token", example = "10.5")
    Double bestPerformance;

    @JsonProperty("worst_asset")
    @Schema(description = "The token that made the least money in percentage", example = "ETH")
    String worstAsset;

    @JsonProperty("worst_performance")
    @Schema(description = "The price variation, in percentage, of the worst token", example = "-3.2")
    Double worstPerformance;

    public WalletEvaluationOutputDTO() {
        super();
    }

    public WalletEvaluationOutputDTO(
            BigDecimal total, String bestAsset, Double bestPerformance, String worstAsset, Double worstPerformance) {
        this.total = total;
        this.bestAsset = bestAsset;
        this.bestPerformance = bestPerformance;
        this.worstAsset = worstAsset;
        this.worstPerformance = worstPerformance;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("total", total)
                .append("bestAsset", bestAsset)
                .append("bestPerformance", bestPerformance)
                .append("worstAsset", worstAsset)
                .append("worstPerformance", worstPerformance)
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

        WalletEvaluationOutputDTO that = (WalletEvaluationOutputDTO) o;

        return new EqualsBuilder().append(total, that.total)
                .append(bestAsset, that.bestAsset)
                .append(bestPerformance, that.bestPerformance)
                .append(worstAsset, that.worstAsset)
                .append(worstPerformance, that.worstPerformance)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(total)
                .append(bestAsset)
                .append(bestPerformance)
                .append(worstAsset)
                .append(worstPerformance)
                .toHashCode();
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getBestAsset() {
        return bestAsset;
    }

    public void setBestAsset(String bestAsset) {
        this.bestAsset = bestAsset;
    }

    public Double getBestPerformance() {
        return bestPerformance;
    }

    public void setBestPerformance(Double bestPerformance) {
        this.bestPerformance = bestPerformance;
    }

    public String getWorstAsset() {
        return worstAsset;
    }

    public void setWorstAsset(String worstAsset) {
        this.worstAsset = worstAsset;
    }

    public Double getWorstPerformance() {
        return worstPerformance;
    }

    public void setWorstPerformance(Double worstPerformance) {
        this.worstPerformance = worstPerformance;
    }
}
