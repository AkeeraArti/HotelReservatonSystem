package com.hotel.reservation.exception;

import lombok.Data;

/**
 * @author arti
 *
 */

@Data
public class HotelReservationException extends Exception {

	private String errorMessage;

	private Integer errorCode;

	public HotelReservationException(String errorMessage, Integer errorCode) {
		super();
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

}
