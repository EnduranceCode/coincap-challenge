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

import com.endurancecode.ccchallenge.api.exception.base.ChallengeException;
import com.endurancecode.ccchallenge.entity.User;
import com.endurancecode.ccchallenge.entity.Wallet;
import com.endurancecode.ccchallenge.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl underTest;

    @Mock
    UserRepository userRepository;

    @Test
    void validateUserWalletOwnershipSuccess() {
        Long userId = 1L;
        Long walletId = 1L;

        User user = new User();
        user.setId(userId);
        Wallet wallet = new Wallet();
        wallet.setId(walletId);
        user.setWallet(wallet);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> underTest.validateUserWalletOwnership(userId, walletId));

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void validateUserWalletOwnershipException() {
        Long userId = 1L;
        Long walletId = 1L;

        User user = new User();
        user.setId(userId);
        Wallet wallet = new Wallet();
        wallet.setId(100L);
        user.setWallet(wallet);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertThrows(ChallengeException.class, () -> underTest.validateUserWalletOwnership(userId, walletId));

        verify(userRepository, times(1)).findById(userId);
    }
}