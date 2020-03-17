package com.hotel.reservation.dto.request;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author arti
 *
 */

@Data
public class RoomSearchRequest implements Serializable {

	private String destination;

	private Date checkInDate;

	private Date checkOutDate;

	private String roomType;
	
}
