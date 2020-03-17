package com.hotel.reservation.bean;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.hotel.reservation.utils.UserType;

import lombok.Data;

/**
 * @author arti
 *
 */

@Data
@Document(indexName = "account", type = "_doc", createIndex = false)
public class Account implements Serializable {

	@Id
	private String accountId;

	private String accountName;

	private String hotelId;

	private String hotelName;

	private UserType userType;

}
