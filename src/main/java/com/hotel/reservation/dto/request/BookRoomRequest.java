package com.hotel.reservation.dto.request;

import java.util.Date;

import lombok.Data;

/**
 * @author arti
 *
 */

@Data
public class BookRoomRequest {

	private String hotelId;

	private String emailId;

	private String roomType;

	private Date checkInDate;

	private Date checkOutDate;

	private Integer roomId;

}
