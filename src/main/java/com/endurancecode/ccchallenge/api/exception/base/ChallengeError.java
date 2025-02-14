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

package com.endurancecode.ccchallenge.api.exception.base;

public enum ChallengeError {
    ASSET_ALREADY_EXISTS("The provided asset already exists in the user's"),
    ASSET_NOT_FOUND_API("The provided asset was not found in the CoinCap API"),
    ASSET_NOT_FOUND_DB("The provided asset was not found in the database"),
    USER_DOES_NOT_OWN_WALLET("The provided user does not own the given wallet"),
    USER_NOT_FOUND("The provided user was not found in the database"),
    WALLET_NOT_FOUND("The provided wallet was not found in the database"),
    PERSISTENCE_ERROR("An error occurred while trying to persist the entity");

    private final String message;

    ChallengeError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
