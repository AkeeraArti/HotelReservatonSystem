/**
 * 
 */
package com.hotel.reservation.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.hotel.reservation.bean.Account;
import com.hotel.reservation.bean.Hotel;
import com.hotel.reservation.bean.Room;
import com.hotel.reservation.bean.User;
import com.hotel.reservation.dto.request.AccountRequest;
import com.hotel.reservation.dto.request.BookRoomRequest;
import com.hotel.reservation.dto.request.CreateRoomTypeRequest;
import com.hotel.reservation.dto.request.RoomSearchRequest;
import com.hotel.reservation.dto.request.UserLoginRequest;
import com.hotel.reservation.dto.request.UserRegisterRequest;
import com.hotel.reservation.dto.response.AccountResponse;
import com.hotel.reservation.dto.response.BookedRoomResponse;
import com.hotel.reservation.dto.response.BookingReportResponse;
import com.hotel.reservation.dto.response.HotelSearchResponse;
import com.hotel.reservation.dto.response.RoomSearchResponse;
import com.hotel.reservation.dto.response.RoomSearchResponses;
import com.hotel.reservation.exception.HotelReservationException;
import com.hotel.reservation.repository.AccountRepository;
import com.hotel.reservation.repository.HotelRepository;
import com.hotel.reservation.repository.RoomRepository;
import com.hotel.reservation.repository.UserRepository;
import com.hotel.reservation.service.HotelReservationService;
import com.hotel.reservation.utils.BookingStatus;
import com.hotel.reservation.utils.UserType;

/**
 * @author arti
 *
 */
@Component
public class HotelReservationServiceImpl implements HotelReservationService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HotelRepository hotelRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Override
	public AccountResponse createAccount(AccountRequest request) throws HotelReservationException {

		AccountResponse response = new AccountResponse();
		// create account for the admin
		Account newAccount = new Account();
		Account adminAccount = accountRepository.findByAccountId(request.getOldAccountId());
		if (!ObjectUtils.isEmpty(adminAccount)) {
			// check account is already created by super admin for particular hotel
			if (UserType.SUPER_ADMIN.equals(adminAccount.getUserType())
					&& !adminAccount.getHotelName().equals(request.getHotelName())) {
				throw new HotelReservationException("Account for " + request.getHotelName() + " already exist", 4000);
			} else {
				newAccount.setAccountName(request.getAccountName());
				newAccount.setAccountId(UUID.randomUUID().toString());
				newAccount.setUserType(UserType.ADMIN);
				// based on the user type create new account for new hotel or existing hotel
				if (UserType.SUPER_ADMIN.equals(adminAccount.getUserType())) {
					newAccount.setHotelId(UUID.randomUUID().toString());
					newAccount.setHotelName(request.getHotelName());
				} else if (UserType.ADMIN.equals(adminAccount.getUserType())) {
					newAccount.setHotelId(adminAccount.getHotelId());
					newAccount.setHotelName(adminAccount.getHotelName());
				}
			}

		}
		// save account
		accountRepository.save(newAccount);
		BeanUtils.copyProperties(newAccount, response);
		return response;

	}

	@Override
	public String registerUser(UserRegisterRequest request) throws HotelReservationException {

		User User = userRepository.findByEmailId(request.getEmailId());
		if (!ObjectUtils.isEmpty(User)) {
			// check user is alreay exist
			throw new HotelReservationException("User for this " + request.getEmailId() + " already register", 4000);
		} else {
			User = new User();
			BeanUtils.copyProperties(User, request);
			User.setUserType(UserType.VISITOR);
			User.setUserId(UUID.randomUUID().toString());
		}
		User = userRepository.save(User);
		if (!ObjectUtils.isEmpty(User)) {
			return "User register successfully";
		} else {
			return "User not register";
		}

	}

	@Override
	public String loginUser(UserLoginRequest request) {
		User User = userRepository.findByEmailIdAndPassword(request.getEmailId(), request.getPassword());
		// check user is present or not
		if (!ObjectUtils.isEmpty(User)) {
			return "Login successfully";
		} else {
			return "EmailId or Password that you've entered is incorrect";
		}
	}

	@Override
	public RoomSearchResponses searchRoom(RoomSearchRequest request) {
		RoomSearchResponses response = new RoomSearchResponses();
		List<RoomSearchResponse> roomSearchResponses = new ArrayList<>();
		// find all the hotels for the destination
		List<Hotel> hotels = hotelRepository.findByDestination(request.getDestination());
		Set<String> hotelIds = new HashSet<>();
		if (!CollectionUtils.isEmpty(hotels)) {
			for (Hotel hotel : hotels) {
				hotelIds.add(hotel.getId());
			}
		}
		// find all rooms for hotel
		List<Room> rooms = roomRepository.findByHotelIdIn(hotelIds);
		if (!CollectionUtils.isEmpty(rooms)) {
			for (Room room : rooms) {
				// validate room is available for reservation or not
				if (isValidRoom(room, request.getRoomType(), request.getCheckInDate(), request.getCheckOutDate())) {
					RoomSearchResponse roomSearchResponse = new RoomSearchResponse();
					BeanUtils.copyProperties(roomSearchResponse, room);
					// add room in the searchable response
					roomSearchResponses.add(roomSearchResponse);
				}

			}
		}
		List<HotelSearchResponse> hotelSearchResponses = new ArrayList<>();

		if (!CollectionUtils.isEmpty(hotels)) {
			for (Hotel hotel : hotels) {
				// add all rooms to appropriate hotel
				List<RoomSearchResponse> responseRooms = roomSearchResponses.stream()
						.filter(room -> room.getHotelId().equals(hotel.getId())).collect(Collectors.toList());
				if (!CollectionUtils.isEmpty(responseRooms)) {
					HotelSearchResponse hotelResponse = new HotelSearchResponse();
					BeanUtils.copyProperties(hotelResponse, hotel);
					hotelResponse.setRooms(responseRooms);
					hotelSearchResponses.add(hotelResponse);
				}

			}
		}

		response.setHotelSearchResponses(hotelSearchResponses);
		return response;

	}

	// check is room is avaiable for booking
	private boolean isValidRoom(Room room, String requestedRoomType, Date requestedCheckin, Date requestedCheckout) {
		boolean isvalidRoom = false;
		if (room.getRoomType().toLowerCase().equals(requestedRoomType)) {
			if (ObjectUtils.isEmpty(room.getCheckInDate()) && ObjectUtils.isEmpty(room.getCheckInDate())
					&& BookingStatus.AVAILABLE.equals(room.getStatus())) {
				isvalidRoom = true;
			} else if (!ObjectUtils.isEmpty(room.getCheckInDate()) && !ObjectUtils.isEmpty(room.getCheckInDate())) {
				if (requestedCheckin.getTime() != room.getCheckInDate().getTime()
						&& requestedCheckin.after(room.getCheckInDate())
						&& requestedCheckin.before(room.getCheckOutDate())
						&& requestedCheckout.after(room.getCheckInDate())
						&& requestedCheckout.before(room.getCheckOutDate())) {
					isvalidRoom = true;
				}
			}
		}
		return isvalidRoom;
	}

	@Override
	public String bookRoom(BookRoomRequest request) {
		// fetch all bookable rooms
		List<Room> rooms = roomRepository.getBookableRoom(request);
		Room bookedRoom = new Room();
		boolean isbookable = true;
		if (CollectionUtils.isEmpty(rooms)) {
			for (Room room : rooms) {
				if (!isValidRoom(room, request.getRoomType(), request.getCheckInDate(), request.getCheckOutDate())) {
					isbookable = false;
				}
			}
			if (isbookable) {
				BeanUtils.copyProperties(rooms, request);
				bookedRoom.setBookedBy(request.getEmailId());
				// book the room
				bookedRoom = roomRepository.save(bookedRoom);
			}
		}

		if (!ObjectUtils.isEmpty(bookedRoom)) {
			return "Room booked successfully";
		} else {
			return "Room is not available choose some other date or room";
		}

	}

	@Override
	public String addRoomType(CreateRoomTypeRequest request) throws HotelReservationException {
		Account Account = accountRepository.findByAccountIdAndHotelId(request.getAccountId(), request.getHotelId());
		// check account is valid
		if (ObjectUtils.isEmpty(Account) || !UserType.ADMIN.equals(Account.getUserType())) {
			throw new HotelReservationException("Add room type is not allow for this account " + request.getAccountId()
					+ " or hotelId " + request.getHotelId(), 4000);
		} else {
			Room room = roomRepository.findByRoomIdAndRoomType(request.getRoomId(), request.getRoomType());
			if (!ObjectUtils.isEmpty(room)) {
				throw new HotelReservationException(
						"Room type is already exist. Please choose other room no or update this room", 4000);
			} else {
				room = new Room();
				BeanUtils.copyProperties(request, room);
				room.setStatus(BookingStatus.AVAILABLE);
				// add room type
				room = roomRepository.save(room);
				if (ObjectUtils.isEmpty(room)) {
					return "Room type added successfully";
				} else {
					throw new HotelReservationException("Internal server error", 500);
				}
			}
		}

	}

	@Override
	public BookingReportResponse viewBookingReport(String hotelId, String accountId) throws HotelReservationException {
		BookingReportResponse response = new BookingReportResponse();
		List<BookedRoomResponse> bookedRoomResponseList = new ArrayList<>();
		Account Account = accountRepository.findByAccountIdAndHotelId(accountId, hotelId);
		// check account is valid
		if (ObjectUtils.isEmpty(Account) || !UserType.ADMIN.equals(Account.getUserType())) {
			throw new HotelReservationException(
					"View booking report not allowed for account id " + accountId + " and hotel id" + hotelId, 4000);
		} else {
			// get booked room record
			List<Room> bookedRoomRecords = roomRepository.getBookedRoom(hotelId);
			if (!CollectionUtils.isEmpty(bookedRoomRecords)) {
				for (Room bookedRoomRecord : bookedRoomRecords) {
					BookedRoomResponse bookedRoomResponse = new BookedRoomResponse();
					BeanUtils.copyProperties(bookedRoomRecord, bookedRoomResponse);
					bookedRoomResponseList.add(bookedRoomResponse);
				}
				response.setBookedRoomResponses(bookedRoomResponseList);
			}

			return response;
		}

	}

}
