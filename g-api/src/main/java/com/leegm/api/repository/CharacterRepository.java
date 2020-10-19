package com.leegm.api.repository;

import com.leegm.api.domain.Character;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends ReactiveMongoRepository<Character, String> {
}
