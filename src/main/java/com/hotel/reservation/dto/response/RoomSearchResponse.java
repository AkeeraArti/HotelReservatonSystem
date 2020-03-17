package com.hotel.reservation.dto.response;

import java.util.Date;

import com.hotel.reservation.utils.BookingStatus;

import lombok.Data;

/**
 * @author arti
 *
 */

@Data
public class RoomSearchResponse {

	private String roomType;

	private Integer roomId;

	private Double price;

	private String hotelId;

	private Date checkInDate;

	private Date checkOutDate;

}
