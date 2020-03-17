package com.hotel.reservation.repository;

import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.hotel.reservation.bean.Room;
import com.hotel.reservation.dto.request.BookRoomRequest;
import com.hotel.reservation.utils.BookingStatus;

@Repository
public interface RoomRepository extends ElasticsearchRepository<Room, String> {

	List<Room> findByHotelIdIn(Iterable<String> hotelIds);

	Room findByRoomNoAndRoomType(Integer roomNo, String roomType);

	default List<Room> getBookableRoom(BookRoomRequest request) {
		BoolQueryBuilder queryFinal = QueryBuilders.boolQuery();
		queryFinal.must(QueryBuilders.termQuery("hotelId", request.getHotelId()));
		queryFinal.must(QueryBuilders.termQuery("roomType", request.getRoomType()));
		queryFinal.must(QueryBuilders.termQuery("roomNo", request.getRoomNo()));

		SearchQuery searchQueryName = new NativeSearchQueryBuilder().withQuery(queryFinal).build();
		return search(searchQueryName).getContent();

	}

	default List<Room> getBookedRoom(String hotelId) {
		BoolQueryBuilder queryFinal = QueryBuilders.boolQuery();
		queryFinal.must(QueryBuilders.termQuery("hotelId", hotelId));
		queryFinal.must(QueryBuilders.termQuery("status", BookingStatus.RESERVE.toString()));
		
		SearchQuery searchQueryName = new NativeSearchQueryBuilder().withQuery(queryFinal).build();
		return search(searchQueryName).getContent();

	}

}
