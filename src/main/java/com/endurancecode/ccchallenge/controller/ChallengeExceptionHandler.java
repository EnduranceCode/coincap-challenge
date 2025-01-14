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

import com.endurancecode.ccchallenge.controller.constants.ControllerConstants;
import com.endurancecode.ccchallenge.api.dto.ErrorDTO;
import com.endurancecode.ccchallenge.api.exception.base.ChallengeException;
import com.endurancecode.ccchallenge.api.response.ChallengeResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class ChallengeExceptionHandler<T> {

    private static final String MSG_STATUS_ERROR = ControllerConstants.MSG_STATUS_ERROR;
    private static final String MSG_CODE_ERROR = ControllerConstants.MSG_CODE_SERVER_ERROR;

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ChallengeResponse<T> unhandledException(HttpServletRequest request, Exception exception) {

        return new ChallengeResponse<>(MSG_STATUS_ERROR, MSG_CODE_ERROR, exception.getMessage());
    }

    @ExceptionHandler(
            {ChallengeException.class, EmptyResultDataAccessException.class, HttpMessageNotReadableException.class}
    )
    @ResponseBody
    public ChallengeResponse<List<ErrorDTO>> handledException(final HttpServletRequest request, final HttpServletResponse response, final ChallengeException exception) {

        response.setStatus(exception.getCode());

        return new ChallengeResponse<>(MSG_STATUS_ERROR, String.valueOf(exception.getCode()), exception.getMessage(), exception.getErrors());
    }
}
