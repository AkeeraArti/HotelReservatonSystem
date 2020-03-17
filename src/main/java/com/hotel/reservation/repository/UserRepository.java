package com.hotel.reservation.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.hotel.reservation.bean.User;

/**
 * @author arti
 *
 */

@Repository
public interface UserRepository extends ElasticsearchRepository<User, String> {

	User findByEmailId(String emailId);

	User findByEmailIdAndPassword(String emailId, String password);

}
