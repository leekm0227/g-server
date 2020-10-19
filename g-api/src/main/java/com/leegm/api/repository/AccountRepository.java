package com.leegm.api.repository;

import com.leegm.api.domain.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Optional;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, String> {

    Flux<Account> findByPlatformId(String platformId);
}
