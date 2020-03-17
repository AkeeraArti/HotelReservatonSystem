package com.hotel.reservation.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.hotel.reservation.bean.Account;

/**
 * @author arti
 *
 */

@Repository
public interface AccountRepository extends ElasticsearchRepository<Account, String> {

	Account findByAccountId(String oldAccountId);

	Account findByAccountIdAndHotelId(String accountId, String hotelId);

}
