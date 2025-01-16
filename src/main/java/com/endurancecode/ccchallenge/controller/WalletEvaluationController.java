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

package com.endurancecode.ccchallenge.controller;

import com.endurancecode.ccchallenge.api.WalletEvaluationControllerAPI;
import com.endurancecode.ccchallenge.api.dto.WalletEvaluationInputDTO;
import com.endurancecode.ccchallenge.api.dto.WalletEvaluationOutputDTO;
import com.endurancecode.ccchallenge.api.response.ChallengeResponse;
import com.endurancecode.ccchallenge.controller.constants.ControllerConstants;
import com.endurancecode.ccchallenge.service.WalletEvaluationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(
        ControllerConstants.API_VERSION_1 + ControllerConstants.API_RESOURCE_WALLET_EVALUATION
)
public class WalletEvaluationController implements WalletEvaluationControllerAPI {
    private static final String MSG_STATUS_OK = ControllerConstants.MSG_STATUS_OK;
    private static final String MSG_CODE_OK = ControllerConstants.MSG_CODE_OK;
    private static final String MSG_OK = ControllerConstants.MSG_SUCCESS;


    private final WalletEvaluationService walletEvaluationService;

    public WalletEvaluationController(WalletEvaluationService walletEvaluationService) {
        this.walletEvaluationService = walletEvaluationService;
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ChallengeResponse<WalletEvaluationOutputDTO> evaluate(
            @RequestBody(required = true) List<WalletEvaluationInputDTO> tokens
    ) {

        return new ChallengeResponse<>(MSG_STATUS_OK, MSG_CODE_OK, MSG_OK, walletEvaluationService.evaluate(tokens));
    }
}
