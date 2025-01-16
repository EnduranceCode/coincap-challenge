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

package com.endurancecode.ccchallenge.api;

import com.endurancecode.ccchallenge.api.dto.WalletEvaluationInputDTO;
import com.endurancecode.ccchallenge.api.dto.WalletEvaluationOutputDTO;
import com.endurancecode.ccchallenge.api.response.ChallengeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(
        name = "Wallet evaluation Controller",
        description = "Evaluates a wallet's performance based on a list of input tokens and their average buy values."
)
public interface WalletEvaluationControllerAPI {

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
    @Operation(
            summary = "Evaluate Wallet Performance",
            description = "Evaluates a wallet's performance based on a list of input tokens " +
                    "and their average buy values. Calculates the total current value of the wallet " +
                    "and identifies the best and worst performing assets. Performance is calculated using the formula:\n" +
                    "((currentValue / averageBuyValue) - 1) * 100. Tokens without matching symbols or prices are skipped."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200", description = "Wallet evaluated",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = WalletEvaluationOutputDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    ChallengeResponse<WalletEvaluationOutputDTO> evaluate(
            @RequestBody(
                    description = "List of tokens with their symbols and average buy values", required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = WalletEvaluationInputDTO.class),
                            examples = @ExampleObject(
                                    name = "Example payload",
                                    value = "[{\"symbol\": \"BTC\", \"quantity\": 0.12345, \"averageBuyValue\": 37870.5058} ]"
                            )
                    )
            )
            List<WalletEvaluationInputDTO> tokens
    );
}
