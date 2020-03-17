package com.hotel.reservation.dto.response;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class BookingReportResponse implements Serializable {

	List<BookedRoomResponse> bookedRoomResponses;

}
