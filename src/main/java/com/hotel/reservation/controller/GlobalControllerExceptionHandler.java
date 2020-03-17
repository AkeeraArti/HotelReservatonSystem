package com.hotel.reservation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hotel.reservation.exception.HotelReservationException;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(HotelReservationException.class)
	@ResponseBody
	public ResponseEntity<Object> handleBusinessException(HotelReservationException exception) {
		logger.error("HotelReservationException:------------ " + exception);
		return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);

	}

}
