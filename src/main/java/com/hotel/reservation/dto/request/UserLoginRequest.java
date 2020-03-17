package com.hotel.reservation.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserLoginRequest {
	
	@NotBlank(message = "email id a mandatory field")
	private String emailId;

	@NotBlank(message = "password a mandatory field")
	private String password;

}
