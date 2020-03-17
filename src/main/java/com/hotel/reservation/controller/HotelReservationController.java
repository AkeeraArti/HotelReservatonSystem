package com.hotel.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import com.hotel.reservation.service.HotelReservationService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author arti
 *
 */

@RestController
@RequestMapping("/")
public class HotelReservationController {

	@Autowired
	private HotelReservationService service;

	/*
	 * create admin account for the hotel
	 */
	@PostMapping("create/account")
	@ApiOperation(value = "Create account", response = AccountResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Account created successfully") })
	public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest request)
			throws HotelReservationException {

		AccountResponse response = service.createAccount(request);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	/*
	 * register user account for the hotel
	 */
	@PostMapping("register/user")
	@ApiOperation(value = "Register user", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Register user successfully") })
	public ResponseEntity<String> registerUser(@RequestBody UserRegisterRequest request)
			throws HotelReservationException {

		String response = service.registerUser(request);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	/*
	 * login user for application
	 */
	@PostMapping("login/user")
	@ApiOperation(value = "Login user", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Login user successfully") })
	public ResponseEntity<String> loginUser(@RequestBody UserLoginRequest request) throws HotelReservationException {

		String response = service.loginUser(request);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	/*
	 * search room for the hotel booking
	 */
	@PostMapping("search/rooms")
	@ApiOperation(value = "Search rooms", response = RoomSearchResponses.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetch rooms successfully") })
	public ResponseEntity<RoomSearchResponses> searchRoom(@RequestBody RoomSearchRequest request)
			throws HotelReservationException {
		RoomSearchResponses response = service.searchRoom(request);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	/*
	 * book room of the hotel
	 */
	@PostMapping("book/room")
	@ApiOperation(value = "Search rooms", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetch rooms successfully") })
	public ResponseEntity<String> bookRoom(@RequestBody BookRoomRequest request) throws HotelReservationException {
		String response = service.bookRoom(request);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	/*
	 * add room type by admin of the hotel
	 */
	@PostMapping("add/room_type")
	@ApiOperation(value = "Add room type", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Add room type successfully") })
	public ResponseEntity<String> addRoomType(@RequestBody CreateRoomTypeRequest request)
			throws HotelReservationException {
		String response = service.addRoomType(request);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	/*
	 * view booking report of the hotel
	 */
	@GetMapping("view/booking_report")
	@ApiOperation(value = "Add room type", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetch booking records successfully") })
	public ResponseEntity<BookingReportResponse> viewBookingReport(@RequestParam String hotelId, String accountId)
			throws HotelReservationException {
		BookingReportResponse response = service.viewBookingReport(hotelId, accountId);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
