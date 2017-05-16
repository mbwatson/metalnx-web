/*
 * Copyright (c) 2015-2017, Dell EMC
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.emc.metalnx.controller;

import com.emc.metalnx.core.domain.exceptions.DataGridConnectionRefusedException;
import com.emc.metalnx.core.domain.exceptions.DataGridMissingPathOnTicketException;
import com.emc.metalnx.core.domain.exceptions.DataGridNullTicketException;
import com.emc.metalnx.core.domain.exceptions.DataGridTicketNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionController {
    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

	@ExceptionHandler(DataGridConnectionRefusedException.class)
	public String handleConnectionRefusedException(HttpServletResponse response) {
        logger.error("Connection refused");
		response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
		return "errors/serverNotResponding";
	}

	@ExceptionHandler(DataGridNullTicketException.class)
	public ResponseEntity<String> handleNullTicketException() {
        logger.error("Ticket is null");
		return new ResponseEntity<>("Ticket is null", HttpStatus.CONFLICT);
	}

	@ExceptionHandler(DataGridMissingPathOnTicketException.class)
	public ResponseEntity<String> handleMissingPathOnTicketException() {
        logger.error("Ticket missing path");
		return new ResponseEntity<>("Ticket missing path", HttpStatus.CONFLICT);
	}

    @ExceptionHandler(DataGridTicketNotFoundException.class)
    public ResponseEntity<String> handleTicketNotFoundException() {
        logger.error("Ticket not found");
        return new ResponseEntity<>("Ticket not found", HttpStatus.NOT_FOUND);
    }
}
