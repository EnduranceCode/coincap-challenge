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

package com.endurancecode.ccchallenge.service.impl;

import com.endurancecode.ccchallenge.api.dto.ErrorDTO;
import com.endurancecode.ccchallenge.api.exception.UserNotFoundException;
import com.endurancecode.ccchallenge.api.exception.WalletOwnershipException;
import com.endurancecode.ccchallenge.api.exception.base.ChallengeError;
import com.endurancecode.ccchallenge.api.exception.base.ChallengeException;
import com.endurancecode.ccchallenge.entity.User;
import com.endurancecode.ccchallenge.repository.UserRepository;
import com.endurancecode.ccchallenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

import static java.lang.String.format;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void validateUserWalletOwnership(Long userId, Long walletId) throws ChallengeException {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            LOGGER.info(() -> format("User with id %s found in the database", userId));

            if (user.getWallet() != null && user.getWallet().getId().equals(walletId)) {
                LOGGER.info(() -> format("User with id %s owns the wallet with id %s", userId, walletId));
            } else {
                String message = format("User with id %s does not own the wallet with id %s", userId, walletId);
                LOGGER.warning(message);
                throw new WalletOwnershipException(message, new ErrorDTO(ChallengeError.USER_DOES_NOT_OWN_WALLET));
            }
        } else {
            String message = format("User with id %s was not found in the database", userId);
            LOGGER.warning(message);
            throw new UserNotFoundException(message, new ErrorDTO(ChallengeError.USER_NOT_FOUND));
        }
    }
}
