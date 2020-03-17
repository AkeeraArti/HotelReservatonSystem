package com.hotel.reservation.dto.request;

import java.io.Serializable;

import lombok.Data;

/**
 * @author arti
 *
 */

@Data
public class CreateRoomTypeRequest implements Serializable {

	private String roomType;

	private Integer roomId;

	private String hotelId;

	private Double price;

	private String accountId;
}
