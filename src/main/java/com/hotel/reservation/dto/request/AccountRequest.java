package com.hotel.reservation.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @author arti
 *
 */

@Data
public class AccountRequest implements Serializable {

	@NotBlank(message = "account Id  a mandatory field")
	private String oldAccountId;

	@NotBlank(message = " hotel name  a mandatory field")
	private String hotelName;

	@NotBlank(message = " accont name a mandatory field")
	private String accountName;

}
