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

import com.endurancecode.ccchallenge.api.UserWalletControllerAPI;
import com.endurancecode.ccchallenge.api.dto.WalletDTO;
import com.endurancecode.ccchallenge.controller.constants.ControllerConstants;
import com.endurancecode.ccchallenge.entity.Wallet;
import com.endurancecode.ccchallenge.api.exception.base.ChallengeException;
import com.endurancecode.ccchallenge.api.response.ChallengeResponse;
import com.endurancecode.ccchallenge.service.UserService;
import com.endurancecode.ccchallenge.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        ControllerConstants.API_VERSION_1 + ControllerConstants.API_RESOURCE_USER + "/{userId}" + ControllerConstants.API_RESOURCE_WALLET
)
public class UserWalletController implements UserWalletControllerAPI {
    private static final String MSG_STATUS_OK = ControllerConstants.MSG_STATUS_OK;
    private static final String MSG_CODE_OK = ControllerConstants.MSG_CODE_OK;
    private static final String MSG_OK = ControllerConstants.MSG_SUCCESS;

    private final UserService userService;
    private final WalletService walletService;

    @Autowired
    public UserWalletController(UserService userService, WalletService walletService) {
        this.userService = userService;
        this.walletService = walletService;
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{walletId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ChallengeResponse<WalletDTO> getWallet(@PathVariable Long userId, @PathVariable Long walletId) throws ChallengeException {

        userService.validateUserWalletOwnership(userId, walletId);

        WalletDTO data = walletService.findById(walletId);

        return new ChallengeResponse<>(MSG_STATUS_OK, MSG_CODE_OK, MSG_OK, data);
    }
}
