package com.hotel.reservation.service;

import org.springframework.stereotype.Service;

import com.hotel.reservation.dto.request.AccountRequest;
import com.hotel.reservation.dto.request.BookRoomRequest;
import com.hotel.reservation.dto.request.CreateRoomTypeRequest;
import com.hotel.reservation.dto.request.RoomSearchRequest;
import com.hotel.reservation.dto.request.UserLoginRequest;
import com.hotel.reservation.dto.request.UserRegisterRequest;
import com.hotel.reservation.dto.response.AccountResponse;
import com.hotel.reservation.dto.response.BookingReportResponse;
import com.hotel.reservation.dto.response.RoomSearchResponses;
import com.hotel.reservation.exception.HotelReservationException;

@Service
public interface HotelReservationService {

	AccountResponse createAccount(AccountRequest request) throws HotelReservationException;

	String registerUser(UserRegisterRequest request) throws HotelReservationException;

	String loginUser(UserLoginRequest request);

	RoomSearchResponses searchRoom(RoomSearchRequest request);

	String bookRoom(BookRoomRequest request);

	String addRoomType(CreateRoomTypeRequest request) throws HotelReservationException;

	BookingReportResponse viewBookingReport(String hotelId, String accountId) throws HotelReservationException;

}
