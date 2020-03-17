package com.hotel.reservation.bean;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.hotel.reservation.utils.BookingStatus;

import lombok.Data;

@Data
@Document(indexName = "room",type = "_doc", createIndex = false)
public class Room {

	@Id
	private String roomType;

	private Integer roomNo;

	private BookingStatus status;

	private String hotelId;

	private Double price;

	private Date checkInDate;

	private Date checkOutDate;

	private String bookedBy;

}
