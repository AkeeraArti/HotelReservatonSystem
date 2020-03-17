package com.hotel.reservation.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @author arti
 *
 */

@Data
public class UserRegisterRequest implements Serializable {

	@NotBlank(message = "user name a mandatory field")
	private String userName;

	@NotBlank(message = "email id a mandatory field")
	private String emailId;

	@NotBlank(message = "password a mandatory field")
	private String password;

	@NotBlank(message = "confirm password name a mandatory field")
	private String confirmPassword;

	@NotBlank(message = "mobile no a mandatory field")
	private Long mobileNo;

}
