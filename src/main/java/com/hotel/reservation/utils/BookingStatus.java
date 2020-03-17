package com.hotel.reservation.utils;

public enum BookingStatus {

	RESERVE("reserve"), AVAILABLE("available");

	String type;

	BookingStatus(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
