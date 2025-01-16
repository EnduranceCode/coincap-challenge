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

package com.endurancecode.ccchallenge.service;

import com.endurancecode.ccchallenge.api.dto.WalletEvaluationInputDTO;
import com.endurancecode.ccchallenge.api.dto.WalletEvaluationOutputDTO;

import java.util.List;

public interface WalletEvaluationService {

    /**
     * Evaluates a wallet's performance based on a list of input tokens and their average buy values.
     * The method calculates the total current value of the wallet, as well as the best and worst performing assets.
     * <p>
     * Performance is calculated as a percentage using the formula:
     * <pre>
     * performance = ((currentValue / averageBuyValue) - 1) * 100
     * </pre>
     * <p>
     * If a token's symbol does not match any asset fetched from the CoinCap API, or its price is unavailable,
     * it is skipped during the evaluation.
     *
     * @param tokens a list of {@link WalletEvaluationInputDTO} objects representing the tokens in the wallet.
     *               Each object contains the token symbol and its average buy value.
     * @return a {@link WalletEvaluationOutputDTO} object containing the evaluation results:
     * - The total current value of the wallet.
     * - The symbol and performance percentage of the best performing asset.
     * - The symbol and performance percentage of the worst performing asset.
     */
    WalletEvaluationOutputDTO evaluate(List<WalletEvaluationInputDTO> tokens);
}
