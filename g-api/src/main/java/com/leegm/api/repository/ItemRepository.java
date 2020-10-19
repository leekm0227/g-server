package com.leegm.api.repository;

import com.leegm.api.domain.Character;
import com.leegm.api.domain.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends ReactiveMongoRepository<Item, String> {

}
