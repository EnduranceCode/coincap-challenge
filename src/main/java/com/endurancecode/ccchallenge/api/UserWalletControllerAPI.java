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

import com.endurancecode.ccchallenge.api.dto.AssetDTO;
import com.endurancecode.ccchallenge.api.dto.IncreaseQuantityDTO;
import com.endurancecode.ccchallenge.api.dto.WalletDTO;
import com.endurancecode.ccchallenge.api.exception.base.ChallengeException;
import com.endurancecode.ccchallenge.api.response.ChallengeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User's Wallet Controller", description = "Operations related to user's wallet")
public interface UserWalletControllerAPI {

    /**
     * Retrieves the wallet information for the given user with the specified wallet identifier
     *
     * @param userId   the ID of the user
     * @param walletId the ID of the wallet
     * @return a ChallengeResponse containing the WalletDTO
     * @throws ChallengeException if there is an error retrieving the wallet
     */
    @Operation(
            summary = "Retrieve wallet information",
            description = "Retrieves the wallet information for the given user with the specified wallet identifier"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200", description = "Wallet found",
                            content = @Content(
                                    mediaType = "application/json", schema = @Schema(implementation = WalletDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Wallet not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    ChallengeResponse<WalletDTO> getWallet(
            @Parameter(description = "ID of the user", required = true) Long userId,
            @Parameter(description = "ID of the wallet", required = true) Long walletId
    ) throws ChallengeException;

    /**
     * Adds an asset to the user's wallet
     *
     * @param userId   the ID of the user
     * @param walletId the ID of the wallet
     * @param assetDTO the DTO containing the asset information
     * @return a ChallengeResponse containing the updated WalletDTO
     * @throws ChallengeException if there is an error adding the asset
     */
    @Operation(
            summary = "Add asset to wallet",
            description = "Adds an asset to the user's wallet"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200", description = "Asset added",
                            content = @Content(
                                    mediaType = "application/json", schema = @Schema(implementation = WalletDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Wallet not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    ChallengeResponse<WalletDTO> addAsset(
            @PathVariable @Parameter(description = "ID of the user", required = true) Long userId,
            @PathVariable @Parameter(description = "ID of the wallet", required = true) Long walletId,
            @RequestBody @Parameter(
                    description = "Data Transfer Object representing an asset", required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AssetDTO.class),
                            examples = @ExampleObject(
                                    name = "Example payload",
                                    value = "{\"symbol\": \"BTC\", \"quantity\": 1.0, \"price\": 50000.0 }"
                            )
                    )
            ) AssetDTO assetDTO
    ) throws ChallengeException;

    /**
     * Increments the quantity of a specified asset in the user's wallet
     *
     * @param userId              the ID of the user
     * @param walletId            the ID of the wallet
     * @param symbol              the symbol of the asset
     * @param increaseQuantityDTO the DTO containing the quantity to be increased
     * @return a ChallengeResponse containing the updated WalletDTO
     * @throws ChallengeException if there is an error incrementing the asset quantity
     */
    @Operation(
            summary = "Increment asset quantity",
            description = "Increments the quantity of a specified asset in the user's wallet"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200", description = "Asset quantity incremented",
                            content = @Content(
                                    mediaType = "application/json", schema = @Schema(implementation = WalletDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Wallet or asset not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    ChallengeResponse<WalletDTO> incrementAssetQuantity(
            @PathVariable @Parameter(description = "ID of the user", required = true) Long userId,
            @PathVariable @Parameter(description = "ID of the wallet", required = true) Long walletId,
            @PathVariable @Parameter(description = "Symbol of the asset", required = true) String symbol,
            @RequestBody @Parameter(
                    description = "Data Transfer Object that defines the Asset's quantity to increase", required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = IncreaseQuantityDTO.class),
                            examples = @ExampleObject(
                                    name = "Example payload",
                                    value = "{\"increaseQuantity\": 3.0 }"
                            )
                    )
            ) IncreaseQuantityDTO increaseQuantityDTO
    ) throws ChallengeException;
}
