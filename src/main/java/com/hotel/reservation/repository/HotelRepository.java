package com.hotel.reservation.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.hotel.reservation.bean.Hotel;

@Repository
public interface HotelRepository extends ElasticsearchRepository<Hotel, String> {

	List<Hotel> findByDestination(String destination);

}
