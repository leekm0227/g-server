package com.leegm.api.util;

import com.leegm.api.domain.Account;
import com.leegm.api.repository.AccountRepository;
import com.leegm.api.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoJsonSchemaCreator;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Component
public class Data {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    AccountRepository accountRepository;

    @PostConstruct
    public void init() {
        accountRepository.deleteAll();
        characterRepository.deleteAll();
//        testCUDate();
        getSchema();
    }

    void getSchema(){
        MongoJsonSchema schema = MongoJsonSchemaCreator.create(mongoTemplate.getConverter())
                .createSchemaFor(Account.class);

        mongoTemplate.createCollection(Account.class, CollectionOptions.empty().schema(schema));
    }

    void testCUDate() {
        String pid = "testpid";

        Mono<Account> result =accountRepository.save(Account.builder().platformId(pid).build()).flatMap(account -> {
            System.out.println(account.getId());
            System.out.println(account.getCreateAt());
            System.out.println(account.getModifyAt());
            account.setType(2);
            return accountRepository.save(account);
        });

        result.subscribe(account -> {
            System.out.println(account.getId());
            System.out.println(account.getCreateAt());
            System.out.println(account.getModifyAt());
        });
    }
}
