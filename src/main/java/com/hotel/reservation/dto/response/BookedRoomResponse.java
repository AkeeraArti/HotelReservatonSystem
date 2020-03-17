package com.hotel.reservation.dto.response;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class BookedRoomResponse implements Serializable{

	private String roomType;

	private Integer roomNo;

	private String hotelId;

	private Double price;

	private Date checkInDate;

	private Date checkOutDate;

	private String bookedBy;

}
