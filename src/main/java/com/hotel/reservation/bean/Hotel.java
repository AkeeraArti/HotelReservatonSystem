package com.hotel.reservation.bean;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

@Data
@Document(indexName = "hotel",type = "_doc", createIndex = false)
public class Hotel {

	@Id
	private String id;

	private String name;

	private int star;

	private String location;

	private boolean isPoolAvailable;

	private boolean isFreeWifi;

	private List<Room> rooms;

	private String destination;

}
