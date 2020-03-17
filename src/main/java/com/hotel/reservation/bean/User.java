package com.hotel.reservation.bean;

import org.springframework.data.annotation.Id;

/**
 * @author arti
 *
 */

import org.springframework.data.elasticsearch.annotations.Document;

import com.hotel.reservation.utils.UserType;

import lombok.Data;

@Data
@Document(indexName = "user", type = "_doc", createIndex = false)
public class User {

	@Id
	private String userId;

	private String userName;

	private String emailId;

	private String password;

	private Long mobileNo;

	private UserType userType;

}
