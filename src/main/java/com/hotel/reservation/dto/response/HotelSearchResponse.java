package com.hotel.reservation.dto.response;

import java.util.List;

import lombok.Data;

/**
 * @author arti
 *
 */

@Data
public class HotelSearchResponse {

	private String hotelId;

	private String hotelName;

	private int star;

	private String location;

	private boolean isPoolAvailable;

	private boolean isFreeWifi;

	private List<RoomSearchResponse> rooms;

}
