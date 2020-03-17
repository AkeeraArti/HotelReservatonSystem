package com.hotel.reservation.dto.response;

import java.io.Serializable;

import lombok.Data;

/**
 * @author arti
 *
 */

@Data
public class AccountResponse implements Serializable {

	private String accountId;

	private String accontName;

	private String hotelId;

	private String hotelName;

}
